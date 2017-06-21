package ru.translator.ui.chooseLanguage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.App;
import ru.translator.R;
import ru.translator.adapters.LanguageChooseAdapter;
import ru.translator.interfaces.LanguageSelectListener;
import ru.translator.models.Lang;

//экран выбора языка

public class LanguageChooseFragment extends Fragment implements LanguageChooseView{
    @BindView(R.id.langs_list)
            RecyclerView            langsList;
            List<Lang>              languagesToShow;
    @Inject LanguageChoosePresenter mPresenter;
    private LanguageSelectListener  mListener;
    private LanguageChooseAdapter   adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getDeps().inject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    public static LanguageChooseFragment getInstance(boolean dest, String language){
        LanguageChooseFragment languageChooseFragment = new LanguageChooseFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("dest",dest);
        bundle.putString("language",language);
        languageChooseFragment.setArguments(bundle);
        return languageChooseFragment;
    }
    public void setOnLanguageSelectListener(LanguageSelectListener listener) {
        this.mListener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language_choose,container,false);
        ButterKnife.bind(this, view);
        langsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter         = new LanguageChooseAdapter();
        languagesToShow = new ArrayList<>();

        mPresenter.setView(this);
        mPresenter.setInfo(getArguments().getString("language"),getArguments().getBoolean("dest"));

        langsList   .setAdapter(adapter);
        adapter     .setOnClickListener((view1, position) -> mListener.onSelect(languagesToShow.get(position).key,languagesToShow.get(position).value));

        return view;
    }

    @Override
    public void onLangsLoaded(List<Lang> langList) {
        languagesToShow = langList;
        adapter.setInfo(languagesToShow);
    }
}
