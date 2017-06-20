package ru.translator.interfaces;

import ru.translator.util.items.DataBean;



//интерфейс для реакции на изменения в бд(история)
public interface HistoryDataChangeListener {
    void onInsert(DataBean item);
    void onFavChange(int id);

    void onRemoveData();
    void onRemoveFavData();
}
