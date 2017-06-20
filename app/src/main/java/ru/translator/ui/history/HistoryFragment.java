package ru.translator.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.App;
import ru.translator.ui.MainActivity;
import ru.translator.R;
import ru.translator.adapters.HistoryAdapter;
import ru.translator.ui.translate.TranslateFragment;
import ru.translator.util.TextChangeListener;
import ru.translator.util.items.DataBean;



//экран истории

public class HistoryFragment extends Fragment implements HistoryFragmentView{
    HistoryAdapter adapter;

    @BindView(R.id.empty_rel)       RelativeLayout emptyRel;
    @BindView(R.id.search_edt)      EditText searchView;
    @BindView(R.id.history_list)    RecyclerView list;
    @BindView(R.id.not_empty_rel)   LinearLayout notEmptyRel;

    @Inject HistoryFragmentPresenter presenter;

    public static HistoryFragment getInstance(int type){
        HistoryFragment fragment = new HistoryFragment();
        Bundle  bundle = new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getDeps().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        ButterKnife.bind(this, view);

        presenter.setInfo(this,getArguments().getInt("type"));
        presenter.getData();
        presenter.enableDataChangeListener();

        adapter= new HistoryAdapter(getActivity());

        searchView.addTextChangedListener(new TextChangeListener() {
            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(searchView.getText().toString());
            }
        });

        setList();
        adapter.setFavClick((view12, position) -> presenter.addFavoriteItem(adapter.getId(position),!adapter.isItemFav(position)));

        adapter.setonClickListener((view1, position) -> {
            MainActivity.mTranslate.move();
            TranslateFragment.mHistoryClickListener.translateFromHistory(
                    adapter.getItem(position).getLangFrom(),
                    adapter.getItem(position).getLangTo(),
                    adapter.getItem(position).getLangFromText(),
                    adapter.getItem(position).getLangToText(),
                    adapter.getItem(position).getTextFrom());

        });
        return view;
    }
    private void setList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }
    private void whatToShow(){
        if(adapter.getItemCount()==0){
            emptyRel.setVisibility(View.VISIBLE);
            notEmptyRel.setVisibility(View.GONE);
        }else{
            emptyRel.setVisibility(View.GONE);
            notEmptyRel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadComplete(List<DataBean> list) {
        adapter.setInfo(list);
        whatToShow();
    }

    @Override
    public void onItemInsert(DataBean item) {
        adapter.addItem(item);
        whatToShow();
    }

    @Override
    public void onItemFavChange(int id) {
        adapter.makeItemFav(adapter.getPositionById(id));
        whatToShow();
    }

    @Override
    public void onRemoveData() {
        adapter.setInfo(null);
        whatToShow();
    }

    @Override
    public void onRemoveFavData() {
        adapter.ramoveAllFavs();
        whatToShow();
    }

    @Override
    public void onRemoveItem(int id) {
        adapter.removeItem(adapter.getPositionById(id));
        whatToShow();
    }
}
