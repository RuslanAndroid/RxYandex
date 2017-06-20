package ru.translator.ui.history;

import java.util.List;

import ru.translator.util.items.DataBean;

/**
 * Created by Ruslan on 06.06.2017.
 */

public interface HistoryFragmentView {
    void onLoadComplete(List<DataBean> list);

    void onItemInsert(DataBean item);
    void onItemFavChange(int id);
    void onRemoveData();
    void onRemoveFavData();
    void onRemoveItem(int id);
}
