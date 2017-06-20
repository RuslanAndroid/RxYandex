
package ru.translator.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//сюда генерируется ответ с сервера

public class Def {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    public String pos;
    @SerializedName("ts")
    @Expose
    public String ts;
    @SerializedName("tr")
    @Expose
    public List<Tr> tr = null;

}
