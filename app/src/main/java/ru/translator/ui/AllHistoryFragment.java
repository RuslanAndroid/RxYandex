package ru.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.App;
import ru.translator.R;
import ru.translator.adapters.PagerAdapter;
import ru.translator.repository.DataBase;
import ru.translator.repository.DomainService;
import ru.translator.util.OnTabSelectedListener;


//экран истории и избранного

public class AllHistoryFragment extends Fragment{

    @BindView(R.id.tab_layout)  TabLayout tabLayout;
    @BindView(R.id.delete)      ImageView delete;
    @BindView(R.id.pager)       ViewPager viewPager;
    @Inject
    public DomainService mDataBase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getDeps().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_history,container,false);
        ButterKnife.bind(this, view);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.favorite));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        delete.setOnClickListener(v -> {
            if(viewPager.getCurrentItem()==0){
                mDataBase.removeAllDB();
                Toast.makeText(getActivity(), R.string.clear_history,Toast.LENGTH_SHORT).show();
            }else{
                mDataBase.removeFavDB();
                Toast.makeText(getActivity(), R.string.clear_fav_history,Toast.LENGTH_SHORT).show();
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        return view;
    }
}
