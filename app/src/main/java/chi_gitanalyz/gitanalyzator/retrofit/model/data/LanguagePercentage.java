package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 10.10.2016.
 */
public class LanguagePercentage {
    @SerializedName("rb")
    @Expose
    private Float rb;
    @SerializedName("html")
    @Expose
    private Float html;
    @SerializedName("js")
    @Expose
    private Float js;
    @SerializedName("css")
    @Expose
    private Float css;

    public Float getCss() {
        return css;
    }

    public void setCss(Float css) {
        this.css = css;
    }

    public Float getHtml() {
        return html;
    }

    public void setHtml(Float html) {
        this.html = html;
    }

    public Float getJs() {
        return js;
    }

    public void setJs(Float js) {
        this.js = js;
    }

    public Float getRb() {
        return rb;
    }

    public void setRb(Float rb) {
        this.rb = rb;
    }
}
