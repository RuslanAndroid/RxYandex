package ru.translator.repository;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.List;

import ru.translator.interfaces.FavDataChangeListener;
import ru.translator.interfaces.HistoryDataChangeListener;
import ru.translator.network.Request;
import ru.translator.models.Def;
import ru.translator.models.Lang;
import ru.translator.util.items.DataBean;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DomainService {

    private final   DiskRepository mDiskRepository;
    private         NetworkAPI mAPI;
    private         DataBase mDataBase;

    private HistoryDataChangeListener mHistoryDataChangeListener;
    private FavDataChangeListener mFavDataChangeListener;
    public DomainService( NetworkAPI API , Context context) {
        this.mAPI = API;
        mDiskRepository = new DiskRepository(context);
        mDataBase       = new DataBase(context);
    }


    public Observable<String> getMergedShortTranslate(String textToTranslate, String languageFrom, String languageTo) {
        return Observable.mergeDelayError(
                mDiskRepository
                        .getShortTranslate(textToTranslate+languageFrom+languageTo)
                        .filter(String::isEmpty)
                        .subscribeOn(Schedulers.io()),
                getTranslateShort(textToTranslate,languageFrom,languageTo)
        );
    }

    public Observable<List<Def>> getMergedLongTranslate(String textToTranslate, String languageFrom, String languageTo) {
        return Observable.mergeDelayError(
                mDiskRepository.getLongAnswer(textToTranslate+languageFrom+languageTo).subscribeOn(Schedulers.io()),
                getTranslateFull(textToTranslate,languageFrom,languageTo)
        ).filter(response -> response != null);
    }

    public Observable<List<Lang>> getMergedLangs() {
        return Observable.mergeDelayError(
                mDiskRepository.getLangs().subscribeOn(Schedulers.io()),
                getLangs()
        ).filter(response -> response != null);
    }

    public Observable<List<Lang>> getMergedDirs() {
        return Observable.mergeDelayError(
                mDiskRepository.getDirs().subscribeOn(Schedulers.io()),
                getDirs()
        ).filter(response -> response != null);
    }

    public Observable<List<Lang>> getLangs(){
        return mAPI.getLangs(Request.TRANSLATE_API_KEY,"ru")
                .subscribeOn(Schedulers.io())
                .filter(jsonElement -> jsonElement != null)
                .map(jsonElement -> jsonElement.getAsJsonObject().getAsJsonObject("langs").entrySet())
                .flatMapIterable(list -> list)
                .map(entry -> new Lang(entry.getKey().replace("\"", ""),entry.getValue().toString().replace("\"", "")))
                .toList()
                .map(langs -> {
                    Collections.sort(langs, (s1, s2) -> s1.value.compareToIgnoreCase(s2.value));
                    return langs;
                })
                .doOnNext(mDiskRepository::saveLangs)
                .observeOn(AndroidSchedulers.mainThread(), true)
                .retry(3);
    }

    private Observable<List<Lang>> getDirs(){
        return mAPI.getDirs(Request.DICTINARY_API_KEY)
                .subscribeOn(Schedulers.io())
                .filter(jsonElement -> jsonElement != null)
                .map(JsonElement::toString)
                .map(s -> new Gson().fromJson(s, JsonArray.class))
                .flatMapIterable(list -> list)
                .map(entry -> new Lang(entry.getAsString().split("-")[0],entry.getAsString().split("-")[1]))
                .toList()
                .doOnNext(mDiskRepository::saveDirs)
                .observeOn(AndroidSchedulers.mainThread(), true)
                .retry(3);
    }

    private Observable<String> getTranslateShort(String textToTranslate, String languageFrom, String languageTo){
        return mAPI.translate(Request.TRANSLATE_API_KEY,
                textToTranslate,
                languageTo)
                .subscribeOn(Schedulers.io())
                .map(translateResponse ->  translateResponse.text.get(0))
                .doOnNext(s -> mDiskRepository.saveShortAnswer(s, textToTranslate + languageFrom + languageTo));
    }

    private Observable<List<Def>> getTranslateFull(String textToTranslate, String languageFrom, String languageTo){
        return mAPI.translateDict(
                Request.DICTINARY_API_KEY,
                textToTranslate,
                languageFrom+"-"+languageTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .map(response -> response.def)
                .doOnNext(defs -> mDiskRepository.saveLongAnswer(defs,textToTranslate+languageFrom+languageTo));
    }


    public void setHistoryDataChangeListener(HistoryDataChangeListener historyDataChangeListener) {
        mHistoryDataChangeListener = historyDataChangeListener;
    }

    public void setFavDataChangeListener(FavDataChangeListener favDataChangeListener) {
        mFavDataChangeListener = favDataChangeListener;
    }

    public void openDataBase(){
        mDataBase.open();
    }

    public void closeDataBase(){
        mDataBase.close();
    }

    public void addItemToDB(String txtFrom,String txtTo,String langFrom,String langTo,String langFromText,String langToText){
        mDataBase.addRec(txtFrom,txtTo,langFrom,langTo,langFromText,langToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(dataBean -> mHistoryDataChangeListener.onInsert(dataBean));
    }

    public void addFavoriteItemToDB(int rowId,boolean newValue){
        mDataBase.addFavRec(rowId,newValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(id -> {
                    if(newValue){
                        mDataBase.getItem(id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread(), true)
                                .subscribe(dataBean -> mFavDataChangeListener      .onInsert(dataBean));
                    }else {
                        mFavDataChangeListener      .onFavRemove(id);
                    }
                    mHistoryDataChangeListener  .onFavChange(id);
                });
    }

    public Observable<List<DataBean>> getAllDB(){
        return mDataBase.getAllData();
    }

    public Observable<List<DataBean>> getAllFavoriteDB(){
        return mDataBase.getAllFavoriteData();
    }

    public void removeAllDB() {
        mDataBase.removeAll().
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(o -> {
                    mHistoryDataChangeListener.onRemoveData();
                    mFavDataChangeListener.onRemoveData();
                });
    }

    public void removeFavDB() {
        mDataBase.removeAllFav()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(o -> {
                    mFavDataChangeListener.onRemoveData();
                    mHistoryDataChangeListener.onRemoveFavData();
                });
    }
}
