package chi_gitanalyz.gitanalyzator.retrofit.model.user.validatetoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 22.09.2016.
 */

public class ValidateTokenError
{
    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}