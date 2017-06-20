package ru.translator.interfaces;

import ru.translator.util.items.DataBean;

//интерфейс для реакции на изменения в бд(избранное)
public interface FavDataChangeListener {
    void onInsert(DataBean item);
    void onFavRemove(int id);
    void onRemoveData();
}
