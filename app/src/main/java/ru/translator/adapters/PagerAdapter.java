package ru.translator.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.translator.ui.history.HistoryFragment;
//адаптер слайдера истории
public class PagerAdapter extends FragmentStatePagerAdapter {

    public static int TYPE_HISTORY=0,TYPE_FAVORITE=1;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return HistoryFragment.getInstance(TYPE_HISTORY);
            case 1:
                return HistoryFragment.getInstance(TYPE_FAVORITE);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}