package ru.translator.ui.translate;


/**
 * Created by Ruslan on 08.06.2017.
 */

public interface TranslateFragmentPresenter {
    void setView(TranslateFragmentView fragmentView);
    void createAndStartRecognizer();
    void createAndStartVocalizer(String textToTalk);
    void onPause();
    void doTranslate(String textToTranslate);

    void setLanguage(String fromLang, String toLang, String fromLangText, String toLangText);

    void addItemToDB(String txtFrom, String txtTo);


}
