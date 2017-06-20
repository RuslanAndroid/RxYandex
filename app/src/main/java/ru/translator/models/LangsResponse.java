package ru.translator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//сюда генерируется ответ с сервера


public class LangsResponse {
    @SerializedName("dirs")
    @Expose
    public List<String> dirs = null;
    @SerializedName("langs")
    @Expose
    public Langs langs;

    public class Langs {

        @SerializedName("af")
        @Expose
        public String af;
        @SerializedName("am")
        @Expose
        public String am;
        @SerializedName("ar")
        @Expose
        public String ar;
        @SerializedName("az")
        @Expose
        public String az;
        @SerializedName("ba")
        @Expose
        public String ba;
        @SerializedName("be")
        @Expose
        public String be;
        @SerializedName("bg")
        @Expose
        public String bg;
        @SerializedName("bn")
        @Expose
        public String bn;
        @SerializedName("bs")
        @Expose
        public String bs;
        @SerializedName("ca")
        @Expose
        public String ca;
        @SerializedName("ceb")
        @Expose
        public String ceb;
        @SerializedName("cs")
        @Expose
        public String cs;
        @SerializedName("cy")
        @Expose
        public String cy;
        @SerializedName("da")
        @Expose
        public String da;
        @SerializedName("de")
        @Expose
        public String de;
        @SerializedName("el")
        @Expose
        public String el;
        @SerializedName("en")
        @Expose
        public String en;
        @SerializedName("eo")
        @Expose
        public String eo;
        @SerializedName("es")
        @Expose
        public String es;
        @SerializedName("et")
        @Expose
        public String et;
        @SerializedName("eu")
        @Expose
        public String eu;
        @SerializedName("fa")
        @Expose
        public String fa;
        @SerializedName("fi")
        @Expose
        public String fi;
        @SerializedName("fr")
        @Expose
        public String fr;
        @SerializedName("ga")
        @Expose
        public String ga;
        @SerializedName("gd")
        @Expose
        public String gd;
        @SerializedName("gl")
        @Expose
        public String gl;
        @SerializedName("gu")
        @Expose
        public String gu;
        @SerializedName("he")
        @Expose
        public String he;
        @SerializedName("hi")
        @Expose
        public String hi;
        @SerializedName("hr")
        @Expose
        public String hr;
        @SerializedName("ht")
        @Expose
        public String ht;
        @SerializedName("hu")
        @Expose
        public String hu;
        @SerializedName("hy")
        @Expose
        public String hy;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("is")
        @Expose
        public String is;
        @SerializedName("it")
        @Expose
        public String it;
        @SerializedName("ja")
        @Expose
        public String ja;
        @SerializedName("jv")
        @Expose
        public String jv;
        @SerializedName("ka")
        @Expose
        public String ka;
        @SerializedName("kk")
        @Expose
        public String kk;
        @SerializedName("kn")
        @Expose
        public String kn;
        @SerializedName("ko")
        @Expose
        public String ko;
        @SerializedName("ky")
        @Expose
        public String ky;
        @SerializedName("la")
        @Expose
        public String la;
        @SerializedName("lb")
        @Expose
        public String lb;
        @SerializedName("lt")
        @Expose
        public String lt;
        @SerializedName("lv")
        @Expose
        public String lv;
        @SerializedName("mg")
        @Expose
        public String mg;
        @SerializedName("mhr")
        @Expose
        public String mhr;
        @SerializedName("mi")
        @Expose
        public String mi;
        @SerializedName("mk")
        @Expose
        public String mk;
        @SerializedName("ml")
        @Expose
        public String ml;
        @SerializedName("mn")
        @Expose
        public String mn;
        @SerializedName("mr")
        @Expose
        public String mr;
        @SerializedName("mrj")
        @Expose
        public String mrj;
        @SerializedName("ms")
        @Expose
        public String ms;
        @SerializedName("mt")
        @Expose
        public String mt;
        @SerializedName("ne")
        @Expose
        public String ne;
        @SerializedName("nl")
        @Expose
        public String nl;
        @SerializedName("no")
        @Expose
        public String no;
        @SerializedName("pa")
        @Expose
        public String pa;
        @SerializedName("pap")
        @Expose
        public String pap;
        @SerializedName("pl")
        @Expose
        public String pl;
        @SerializedName("pt")
        @Expose
        public String pt;
        @SerializedName("ro")
        @Expose
        public String ro;
        @SerializedName("ru")
        @Expose
        public String ru;
        @SerializedName("si")
        @Expose
        public String si;
        @SerializedName("sk")
        @Expose
        public String sk;
        @SerializedName("sl")
        @Expose
        public String sl;
        @SerializedName("sq")
        @Expose
        public String sq;
        @SerializedName("sr")
        @Expose
        public String sr;
        @SerializedName("su")
        @Expose
        public String su;
        @SerializedName("sv")
        @Expose
        public String sv;
        @SerializedName("sw")
        @Expose
        public String sw;
        @SerializedName("ta")
        @Expose
        public String ta;
        @SerializedName("te")
        @Expose
        public String te;
        @SerializedName("tg")
        @Expose
        public String tg;
        @SerializedName("th")
        @Expose
        public String th;
        @SerializedName("tl")
        @Expose
        public String tl;
        @SerializedName("tr")
        @Expose
        public String tr;
        @SerializedName("tt")
        @Expose
        public String tt;
        @SerializedName("udm")
        @Expose
        public String udm;
        @SerializedName("uk")
        @Expose
        public String uk;
        @SerializedName("ur")
        @Expose
        public String ur;
        @SerializedName("uz")
        @Expose
        public String uz;
        @SerializedName("vi")
        @Expose
        public String vi;
        @SerializedName("xh")
        @Expose
        public String xh;
        @SerializedName("yi")
        @Expose
        public String yi;
        @SerializedName("zh")
        @Expose
        public String zh;

    }
}
