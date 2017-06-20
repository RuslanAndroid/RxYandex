package ru.translator.util.items;

//модель объекта сохраненного перевода
public class DataBean {
    private int id;
    private String textFrom;
    private String textTo;
    private String langFrom;
    private String langTo;
    private String langFromText;
    private String langToText;
    private boolean fav;
    public DataBean(int id,
                    String textFrom,
                    String textTo,
                    String langFrom,
                    String langTo,
                    String langFromText,
                    String langToText,
                    boolean fav){
        this.id = id;
        this.textFrom = textFrom;
        this.textTo = textTo;
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.langFromText = langFromText;
        this.langToText = langToText;
        this.fav = fav;
    }

    public String getTextFrom() {
        return textFrom;
    }

    public String getTextTo() {
        return textTo;
    }

    public int getId() {
        return id;
    }

    public String getLangTo() {
        return langTo;
    }

    public String getLangFrom() {
        return langFrom;
    }


    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getLangToText() {
        return langToText;
    }

    public String getLangFromText() {
        return langFromText;
    }

    public String toString(){
        return id+"\n"+textFrom+"\n"+textTo+"\n"+langFrom+"\n"+langTo+"\n"+langFromText+"\n"+langToText+"\n"+fav;
    }
}
