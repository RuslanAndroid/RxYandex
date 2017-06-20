
package ru.translator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//сюда генерируется ответ с сервера

public class Syn {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    public String pos;
    @SerializedName("gen")
    @Expose
    public String gen;

    @Override
    public String toString() {
        return text;
    }

}
