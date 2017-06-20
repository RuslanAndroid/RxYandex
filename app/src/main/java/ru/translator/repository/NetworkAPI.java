package ru.translator.repository;


import com.google.gson.JsonElement;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.translator.network.Request;
import ru.translator.models.TranslateFullResponse;
import ru.translator.models.TranslateResponse;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkAPI {

    @GET(Request.URL_SIMPLE)
    Observable<TranslateResponse> translate(@Query("key") String key,
                                            @Query("text") String text,
                                            @Query("lang") String lang);

    @GET(Request.URL_FULL)
    Observable<TranslateFullResponse> translateDict(@Query("key") String key,
                                                    @Query("text") String text,
                                                    @Query("lang") String lang);
    @GET(Request.URL_LANGS)
    Observable<JsonElement> getLangs(@Query("key") String key,
                                     @Query("ui") String ui);
    @GET(Request.URL_DIRS)
    Observable<JsonElement> getDirs(@Query("key") String key);

}
