package ru.translator.deps;


import javax.inject.Singleton;

import dagger.Component;

import ru.translator.ui.MainActivity;
import ru.translator.ui.chooseLanguage.LanguageChooseFragment;
import ru.translator.ui.chooseLanguage.LanguageChoosePresenterImpl;
import ru.translator.ui.AllHistoryFragment;
import ru.translator.ui.history.HistoryFragment;
import ru.translator.ui.translate.TranslateFragment;
import ru.translator.ui.history.HistoryFragmentPresenterImpl;
import ru.translator.ui.translate.TranslateFragmentPresenterImpl;
import ru.translator.repository.NetworkRepository;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkRepository.class,PresenterModule.class})
public interface Deps {
    void inject(TranslateFragmentPresenterImpl translateFragmentPresenterImpl);
    void inject(MainActivity mainActivity);
    void inject(AllHistoryFragment allHistoryFragment);
    void inject(HistoryFragmentPresenterImpl historyFragmentPresenterImpl);
    void inject(HistoryFragment historyFragment);
    void inject(TranslateFragment translateFragment);
    void inject(LanguageChooseFragment languageChooseFragment);

    void inject(LanguageChoosePresenterImpl languageChoosePresenter);
}
