package movie.example.ls.vvmoviemanager.bean;

import android.text.Spannable;

/**
 * Date :  2016/11/25
 */
public class SmailBean4C {

    private Spannable spannable;
    private String string;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SmailBean4C(Spannable spannable, String string) {
        this.spannable = spannable;
        this.string = string;
    }

    public Spannable getSpannable() {
        return spannable;
    }

    public void setSpannable(Spannable spannable) {
        this.spannable = spannable;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
