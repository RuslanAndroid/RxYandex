package ru.translator.ui.history;


import java.util.List;

import javax.inject.Inject;

import ru.translator.App;
import ru.translator.adapters.PagerAdapter;
import ru.translator.interfaces.FavDataChangeListener;
import ru.translator.interfaces.HistoryDataChangeListener;

import ru.translator.repository.DomainService;
import ru.translator.util.items.DataBean;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Ruslan on 06.06.2017.
 */

public class HistoryFragmentPresenterImpl implements HistoryFragmentPresenter{
    @Inject
    public  DomainService mDomainService;
    private HistoryFragmentView mFragmentView;
    private int fragmentType;

    public HistoryFragmentPresenterImpl(){
        App.getDeps().inject(this);
    }

    @Override
    public void setInfo(HistoryFragmentView fragmentView, int fragmentType){
        this.mFragmentView = fragmentView;
        this.fragmentType = fragmentType;
    }

    @Override
    public void getData(){
        if(fragmentType== PagerAdapter.TYPE_HISTORY){
            mDomainService.getAllDB().subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .filter(dataBeen -> dataBeen != null)
                    .subscribe(dataBeen -> mFragmentView.onLoadComplete(dataBeen));
        }else{
            mDomainService.getAllFavoriteDB().subscribeOn(Schedulers.computation())
                     .observeOn(AndroidSchedulers.mainThread(), true)
                    .filter(dataBeen -> dataBeen != null)
                    .subscribe(dataBeen -> mFragmentView.onLoadComplete(dataBeen));
        }
    }

    @Override
    public void enableDataChangeListener(){
        if(fragmentType == PagerAdapter.TYPE_HISTORY){

            mDomainService.setHistoryDataChangeListener(new HistoryDataChangeListener() {
                @Override
                public void onInsert(DataBean item) {
                    mFragmentView.onItemInsert(item);
                }

                @Override
                public void onFavChange(int id) {
                    mFragmentView.onItemFavChange(id);
                }

                @Override
                public void onRemoveData() {
                    mFragmentView.onRemoveData();
                }

                @Override
                public void onRemoveFavData() {
                    mFragmentView.onRemoveFavData();
                }
            });
        }else {

            mDomainService.setFavDataChangeListener(new FavDataChangeListener() {
                @Override
                public void onInsert(DataBean item) {
                    mFragmentView.onItemInsert(item);
                }

                @Override
                public void onFavRemove(int id) {
                    mFragmentView.onRemoveItem(id);
                }

                @Override
                public void onRemoveData() {
                    mFragmentView.onRemoveData();
                }
            });

        }
    }

    @Override
    public void addFavoriteItem(int id, boolean fav) {
        mDomainService.addFavoriteItemToDB(id,fav);
    }

}
