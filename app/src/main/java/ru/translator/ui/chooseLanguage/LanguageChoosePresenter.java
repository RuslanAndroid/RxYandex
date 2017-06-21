package ru.translator.ui.chooseLanguage;


import rx.Observable;

/**
 * Created by Ruslan on 13.06.2017.
 */

public interface LanguageChoosePresenter {
    void setView(LanguageChooseView splashView);
    void setInfo(String language, boolean destinationLanguage);
    void onPause();
   // Observable<Object> getMergedLangs();
}
