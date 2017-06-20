
package ru.translator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//сюда генерируется ответ с сервера

public class Mean {

    @SerializedName("text")
    @Expose
    public String text;

    @Override
    public String toString() {
        return text;
    }
}
