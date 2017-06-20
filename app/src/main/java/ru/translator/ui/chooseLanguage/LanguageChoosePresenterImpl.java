package ru.translator.ui.chooseLanguage;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.translator.App;
import ru.translator.models.Lang;
import ru.translator.repository.DomainService;
import ru.translator.util.NetworkError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import rx.schedulers.Schedulers;

import static ru.translator.App.getContext;

/**
 * Created by Ruslan on 13.06.2017.
 */

public class LanguageChoosePresenterImpl implements LanguageChoosePresenter {
    String language;
    boolean destinationLanguage;
    @Inject
    DomainService service;
    private  List<Lang> languages;
    private  List<Lang> dirs ;
    private  List<Lang> languagesToShow = new ArrayList<>();

    public LanguageChoosePresenterImpl(){
        App.getDeps().inject(this);
    }

    @Override
    public void setInfo(String language, boolean destinationLanguage) {
        this.language = language;
        this.destinationLanguage = destinationLanguage;
    }

    @Override
    public void setView(LanguageChooseView languageChooseView){
        getMergedLangs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnCompleted(() -> {})
                .subscribe(o -> {
                            languagesToShow = new ArrayList<Lang>();
                            for (Lang item : dirs) {
                                String key;
                                String value;
                                if (destinationLanguage){
                                    key = item.key;
                                    value = item.value;
                                }else{
                                    value = item.key;
                                    key = item.value;
                                }
                                if(key.equals(language)){
                                    for(int j =0;j<languages.size();j++){
                                        if(languages.get(j).key.equals(value)){
                                            languagesToShow.add(languages.get(j));
                                        }
                                    }
                                }
                            }
                            languageChooseView.onLangsLoaded(languagesToShow);

                        },
                        throwable -> Toast.makeText(getContext(), new NetworkError(throwable).getAppErrorMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private Observable<List<Lang>> getLangs(){
        return service.getMergedLangs()
                .filter(langList -> langList!= null && langList.size()>0)
                .doOnNext(langList -> languages = langList);
    }

    private Observable<List<Lang>> getDirs(){
        return service.getMergedDirs()
                .filter(list -> list!= null && list.size()>0)
                .doOnNext(list -> dirs = list);
    }

    private Observable<Object> getMergedLangs() {
        return Observable.zip(
                getLangs(), getDirs(), (langList, langList2) -> null
        );
    }
}
