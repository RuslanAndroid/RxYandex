package ru.translator.ui.translate;

import android.support.annotation.StringRes;

import java.util.List;

import ru.translator.models.Tr;
import ru.yandex.speechkit.Recognition;

/**
 * Created by Ruslan on 05.06.2017.
 */

public interface TranslateFragmentView {


    void onShortTranslate(String s);

    void onFullTranslate(List<Tr> trs);

    void onRecordingBegin();
    void onError(@StringRes int resId);
    void onPartialRecognizerResults(Recognition recognition);
    void onRecognitionDone(Recognition recognition);
}
