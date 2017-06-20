package ru.translator;

import android.app.Application;
import android.content.Context;

import ru.translator.deps.DaggerDeps;
import ru.translator.deps.Deps;
import ru.translator.repository.DataBase;
import ru.translator.repository.NetworkRepository;
import ru.yandex.speechkit.SpeechKit;



public class App extends Application {
    String SPEECH_API_KEY="7eeb9bad-2451-4735-9358-4beb0374c724";
    static Context context;
    static Deps deps;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpeechKit.getInstance().configure(this,SPEECH_API_KEY);
        deps = DaggerDeps.builder()
                .networkRepository(new NetworkRepository(getApplicationContext()))
         //       .dB(new DataBase(getApplicationContext()))
                .build();

    }
    public static Context getContext() {
        return context;
    }
    public static Deps getDeps() {
        return deps;
    }
}
