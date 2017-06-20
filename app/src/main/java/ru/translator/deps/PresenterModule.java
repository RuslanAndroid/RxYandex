package ru.translator.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.translator.ui.chooseLanguage.LanguageChoosePresenter;
import ru.translator.ui.chooseLanguage.LanguageChoosePresenterImpl;
import ru.translator.ui.history.HistoryFragmentPresenter;
import ru.translator.ui.history.HistoryFragmentPresenterImpl;

import ru.translator.ui.translate.TranslateFragmentPresenter;
import ru.translator.ui.translate.TranslateFragmentPresenterImpl;

/**
 * Created by Ruslan on 08.06.2017.
 */

@Module
public class PresenterModule {

    @Provides
    HistoryFragmentPresenter mHistoryFragmentPresenter() {
        return new HistoryFragmentPresenterImpl();
    }

    @Provides
    @Singleton
    TranslateFragmentPresenter mTranslateFragmentPresenter() {
        return new TranslateFragmentPresenterImpl();
    }

    @Provides
    @Singleton
    LanguageChoosePresenter mLanguageChoosePresenter() {
        return new LanguageChoosePresenterImpl();
    }
}