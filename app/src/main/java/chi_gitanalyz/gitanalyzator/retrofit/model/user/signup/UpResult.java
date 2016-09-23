
package chi_gitanalyz.gitanalyzator.retrofit.model.user.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpResult
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
