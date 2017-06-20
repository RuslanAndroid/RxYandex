package ru.translator.ui.splash;

import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ru.translator.App;
import ru.translator.models.Lang;
import ru.translator.repository.DomainService;
import ru.translator.util.Data;
import ru.translator.util.NetworkError;
import rx.Observable;

import static ru.translator.App.getContext;

/**
 * Created by Ruslan on 07.06.2017.
 */

public class SplashPresenterImpl implements SplashPresenter{

    @Inject
    DomainService service;

    public SplashPresenterImpl(){
        App.getDeps().inject(this);
    }

    @Override
    public void setView(SplashView splashView){
        getMergedLangs().subscribe(o -> splashView.onLangsLoaded(),
                throwable -> Toast.makeText(getContext(), new NetworkError(throwable).getAppErrorMessage(), Toast.LENGTH_SHORT).show());
    }

    private Observable<List<Lang>> getLangs(){
        return service.getMergedLangs().doOnNext(langs -> Data.languages = langs);
    }

    private Observable<List<Lang>> getDirs(){
        return service.getMergedDirs().doOnNext(list -> Data.dirs=list);

    }

    @Override
    public Observable<Object> getMergedLangs() {
        return Observable.merge(
                getLangs(),getDirs()
        );
    }
}
