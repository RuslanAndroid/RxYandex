package ru.translator.interfaces;


//интерфейс для реакции на клик по элементу истории
public interface HistoryClickListener {
    void translateFromHistory(String fromLang,String toLang,String fromLangText,String toLangText,String textToTranslate);
}
