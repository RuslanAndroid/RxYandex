package ru.translator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


//сюда генерируется ответ с сервера
public class TranslateResponse {
    @SerializedName("code")
    @Expose
    public int code;
    @SerializedName("lang")
    @Expose
    public String lang;
    @SerializedName("text")
    @Expose
    public List<String> text = null;

}
