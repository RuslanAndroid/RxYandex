package ru.translator.ui;

import android.app.AlertDialog;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.App;
import ru.translator.R;
import ru.translator.adapters.MainPagerAdapter;

import ru.translator.interfaces.MoveToTranslate;
import ru.translator.repository.DomainService;
import ru.translator.util.CustomViewPager;

//главная активити
public class MainActivity extends AppCompatActivity {
            public static   MoveToTranslate mTranslate;

    @Inject
    DomainService mDomainService;

    @BindView(R.id.main_pager)          CustomViewPager viewPager;
    @BindView(R.id.bottom_navigation)   BottomNavigationView bottomNavigationView;

    @Override
    protected void onStart() {
        super.onStart();
        mDomainService.openDataBase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDomainService.closeDataBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getDeps().inject(this);
        ButterKnife.bind(this);

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_translate:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_history:
                            viewPager.setCurrentItem(1);
                            break;
                    }
                    return true;
                });

        mTranslate = () -> {
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            viewPager.setCurrentItem(0);
        };

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int backStackEntryCount = fm.getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getResources().getString(R.string.exit_title))
                    .setPositiveButton(R.string.exit, (dialog, which) -> finish())
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
