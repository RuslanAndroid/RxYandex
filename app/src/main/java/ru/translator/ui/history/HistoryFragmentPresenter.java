package ru.translator.ui.history;

/**
 * Created by Ruslan on 08.06.2017.
 */

public interface HistoryFragmentPresenter {
    void setInfo(HistoryFragmentView fragmentView, int fragmentType);
    void getData();
    void enableDataChangeListener();
    void addFavoriteItem(int id, boolean fav);
}
