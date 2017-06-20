
package ru.translator.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//сюда генерируется ответ с сервера

public class Tr {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    public String pos;
    @SerializedName("gen")
    @Expose
    public String gen;
    @SerializedName("syn")
    @Expose
    public List<Syn> syn = null;
    public String synString;
    @SerializedName("mean")
    @Expose
    public List<Mean> mean = null;
    public String meanString;
    @SerializedName("asp")
    @Expose
    public String asp;

}
