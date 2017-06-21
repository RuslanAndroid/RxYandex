package ru.translator.ui.history;



import javax.inject.Inject;

import ru.translator.App;
import ru.translator.adapters.PagerAdapter;
import ru.translator.interfaces.FavDataChangeListener;
import ru.translator.interfaces.HistoryDataChangeListener;

import ru.translator.repository.DomainService;
import ru.translator.util.items.DataBean;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Ruslan on 06.06.2017.
 */

public class HistoryFragmentPresenterImpl implements HistoryFragmentPresenter{
    @Inject
    public  DomainService mDomainService;
    private HistoryFragmentView mFragmentView;
    private int fragmentType;
    private Subscription mSubscription;
    private CompositeSubscription mCompositeSubscription;
    public HistoryFragmentPresenterImpl(){
        App.getDeps().inject(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void setInfo(HistoryFragmentView fragmentView, int fragmentType){
        this.mFragmentView = fragmentView;
        this.fragmentType = fragmentType;
    }

    @Override
    public void getData(){
        if(fragmentType== PagerAdapter.TYPE_HISTORY){
            mSubscription = mDomainService.getAllDB().subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .filter(dataBeen -> dataBeen != null)
                    .subscribe(dataBeen -> mFragmentView.onLoadComplete(dataBeen));
        }else{
            mSubscription = mDomainService.getAllFavoriteDB().subscribeOn(Schedulers.computation())
                     .observeOn(AndroidSchedulers.mainThread(), true)
                    .filter(dataBeen -> dataBeen != null)
                    .subscribe(dataBeen -> mFragmentView.onLoadComplete(dataBeen));
        }
        mCompositeSubscription.add(mSubscription);
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
        mCompositeSubscription.add( mDomainService.addFavoriteItemToDB(id,fav) );
    }

    @Override
    public void onPause() {
        if(mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = new CompositeSubscription();
        }
    }

}
