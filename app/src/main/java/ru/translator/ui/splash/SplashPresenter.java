package ru.translator.ui.splash;

import rx.Observable;

/**
 * Created by Ruslan on 13.06.2017.
 */

public interface SplashPresenter {
    void setView(SplashView splashView);
    Observable<Object> getMergedLangs();
}
