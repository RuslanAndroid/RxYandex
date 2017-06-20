package ru.translator.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import ru.translator.models.Def;
import ru.translator.models.Lang;
import rx.Observable;

public class DiskRepository {

    private static final String CLASSNAME = DiskRepository.class.getCanonicalName();

    private Gson mGson = new Gson();
    private final SharedPreferences sharedPreferences;
    private static String KEY_LANGS = "KEY_LANGS";
    private static String KEY_DIRS = "KEY_DIRS";
    private static String KEY_SHORT = "KEY_SHORT";
    private static String KEY_LONG = "KEY_LONG";

    @Singleton
    public DiskRepository(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveShortAnswer(String response, String key) {
        sharedPreferences.edit().putString(KEY_SHORT + key,response).apply();
    }
    public void saveLangs(List<Lang> list) {
        sharedPreferences.edit().putString(KEY_LANGS,mGson.toJson(list)).apply();
    }
    public void saveDirs(List<Lang> list) {
        sharedPreferences.edit().putString(KEY_DIRS,mGson.toJson(list)).apply();
    }

    public void saveLongAnswer(List<Def> list , String key) {
        sharedPreferences.edit().putString(KEY_LONG + key,mGson.toJson(list)).apply();
    }

    public Observable<String> getShortTranslate(String key) {
        return Observable.fromCallable(() -> sharedPreferences.getString(KEY_SHORT + key, ""));
    }
    public Observable<List<Def>> getLongAnswer(String key) {
        return Observable.fromCallable(() -> {
            String sharedPreferencesString = sharedPreferences.getString(KEY_LONG + key, "");
            List<Def> list = null;
            if (!TextUtils.isEmpty(sharedPreferencesString)) {
                Type type = new TypeToken<List<Def>>(){}.getType();
                list= mGson.fromJson(sharedPreferencesString, type);
            }
            return list;
        });
    }

    public Observable<List<Lang>> getLangs() {
        return Observable.fromCallable(() -> {
            String sharedPreferencesString = sharedPreferences.getString(KEY_LANGS, "");
            List<Lang> list = null;
            if (!TextUtils.isEmpty(sharedPreferencesString)) {
                Type type = new TypeToken<List<Lang>>(){}.getType();
                list= mGson.fromJson(sharedPreferencesString, type);
            }
            return list;
        });
    }
    public Observable<List<Lang>> getDirs() {
        return Observable.fromCallable(() -> {
            String sharedPreferencesString = sharedPreferences.getString(KEY_DIRS, "");
            List<Lang> list = null;
            if (!TextUtils.isEmpty(sharedPreferencesString)) {
                Type type = new TypeToken<List<Lang>>(){}.getType();
                list= mGson.fromJson(sharedPreferencesString, type);
            }
            return list;
        });
    }
}