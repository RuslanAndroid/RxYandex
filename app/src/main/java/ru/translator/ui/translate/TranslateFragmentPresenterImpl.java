package ru.translator.ui.translate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ru.translator.App;
import ru.translator.R;
import ru.translator.models.Mean;
import ru.translator.models.Syn;
import ru.translator.models.Tr;
import ru.translator.repository.DomainService;
import ru.translator.util.NetworkError;
import ru.translator.util.SpeechNRecognicionListener;
import ru.translator.util.StringUtils;
import ru.translator.util.Toaster;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.Vocalizer;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


import static ru.translator.App.getContext;

/**
 * Created by Ruslan on 05.06.2017.
 */

public class TranslateFragmentPresenterImpl extends SpeechNRecognicionListener implements TranslateFragmentPresenter {

    private TranslateFragmentView mFragmentView;
    private Recognizer recognizer;
    private Vocalizer vocalizer;

    @Inject
    public DomainService service;

    Subscription mSubscriptionForSave;

    private boolean recordEnabled;
    private boolean vocalEnabled;
    private String  toLang              ="en",
                    fromLang            ="ru",
                    fromLangText        ="Русский",
                    toLangText          ="Английский";

    public TranslateFragmentPresenterImpl() {
        App.getDeps().inject(this);
        recordEnabled   = true;
        vocalEnabled    = true;
    }

    @Override
    public void setView(TranslateFragmentView fragmentView){
        this.mFragmentView = fragmentView;
    }

    @Override
    public void setLanguage(String fromLang,String toLang, String fromLangText, String toLangText){
        this.fromLang       = fromLang;
        this.toLang         = toLang;
        this.fromLangText   = fromLangText;
        this.toLangText     = toLangText;

    }


    private Observable<Object> saveTimer(String txtFrom,String txtTo){
        return Observable.defer(() -> Observable.just(null)
                .delay(3, TimeUnit.SECONDS))
                .doOnNext(ignore -> addItemToDB(txtFrom, txtTo));
    }

    //новый объект в бд
    @Override
    public void addItemToDB(String txtFrom,String txtTo){
        service.addItemToDB(txtFrom,txtTo,fromLang,toLang,fromLangText,toLangText);
    }

    //запуск записи
    @Override
    public void createAndStartRecognizer() {
        if(recordEnabled) {
            recordEnabled=false;
            resetRecognizer();
            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, TranslateFragmentPresenterImpl.this);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            recognizer.start();
        }
    }

    //запуск воспроизведения
    @Override
    public void createAndStartVocalizer(String textToTalk) {
        if(vocalEnabled) {
            if (TextUtils.isEmpty(textToTalk)) {
                Toast.makeText(getContext(), "Write smth to be vocalized!", Toast.LENGTH_SHORT).show();
            } else {
                vocalEnabled =false;
                resetVocalizer();
                vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, textToTalk, true, Vocalizer.Voice.ERMIL);
                vocalizer.setListener(TranslateFragmentPresenterImpl.this);
                vocalizer.start();
            }
        }
    }


    private void resetRecognizer() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer = null;
        }
    }

    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }
    @Override
    public void onPause(){
        resetRecognizer();
        resetVocalizer();
    }
    @Override
    public void doTranslate(String textToTranslate){
        getMergedTranslate(textToTranslate)
                .onErrorReturn(throwable -> {
                   Toaster.show(new NetworkError(throwable).getAppErrorMessage());
                    return null;
                }).subscribe();
    }

    private Observable<Object> getMergedTranslate(String textToTranslate) {
        return Observable.mergeDelayError(
                translateShort(textToTranslate,fromLang,toLang),
                translateFull(textToTranslate,fromLang,toLang)
        );
    }
    //запрос на перевод текста
    private Observable<String> translateShort(String textToTranslate, String languageFrom, String languageTo){
        return service.getMergedShortTranslate(textToTranslate,languageFrom,languageTo)
                .filter(s -> !s.isEmpty())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    mFragmentView.onShortTranslate(s);
                    if (mSubscriptionForSave != null && !mSubscriptionForSave.isUnsubscribed()) {
                        mSubscriptionForSave.unsubscribe();
                    }
                    mSubscriptionForSave = saveTimer(textToTranslate, s).subscribe();
                });
    }

    //полный перевод

    private Observable<List<Tr>> translateFull(String textToTranslate, String languageFrom, String languageTo){
        return service.getMergedLongTranslate(textToTranslate,languageFrom,languageTo)
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeOn(Schedulers.io())
                .filter(defs -> defs.size()>0)
                .map(defs -> defs.get(0).tr)
                .flatMapIterable(list -> list)
                .filter(tr -> tr.syn != null && tr.syn.size() != 0 && tr.mean != null && tr.mean.size() != 0)
                .map(tr -> {
                        tr.synString= join(", ",  tr.syn);
                        tr.meanString=join(", ", tr.mean);
                    return tr;
                })
                .filter(tr -> !tr.synString.isEmpty() && !tr.meanString.isEmpty())
                .toList()
                .doOnNext(def -> mFragmentView.onFullTranslate(def));
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        super.onRecordingBegin(recognizer);
        mFragmentView.onRecordingBegin();
    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
        mFragmentView.onPartialRecognizerResults(recognition);
    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        super.onPlayingDone(vocalizer);
        vocalEnabled = true;
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        mFragmentView.onRecognitionDone(recognition);
    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        super.onRecordingDone(recognizer);
        recordEnabled = true;
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
        mFragmentView.onError(R.string.error_record);
        resetRecognizer();
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {
        mFragmentView.onError(R.string.error_sound);
        resetVocalizer();
    }
    public String join(String separator, List list){
        String out = "";
        for (Object syn : list){
            out += syn + separator;
        }
        out = out.substring(0, out.length() - separator.length());
        return out;
    }
}
