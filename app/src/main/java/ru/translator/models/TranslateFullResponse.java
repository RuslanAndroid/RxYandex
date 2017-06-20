
package ru.translator.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//сюда генерируется ответ с сервера

public class TranslateFullResponse {
    @SerializedName("def")
    @Expose
    public List<Def> def = null;

}
