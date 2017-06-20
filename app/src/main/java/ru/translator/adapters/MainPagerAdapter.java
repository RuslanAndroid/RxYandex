package ru.translator.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.translator.ui.AllHistoryFragment;
import ru.translator.ui.FirstScreenFragment;
//адаптер слайдера нижнего меню
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FirstScreenFragment();
            case 1:
                return new AllHistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}