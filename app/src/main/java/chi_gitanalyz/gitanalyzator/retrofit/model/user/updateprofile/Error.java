package chi_gitanalyz.gitanalyzator.retrofit.model.user.updateprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Papin on 22.09.2016.
 */

public class Error
{
    @SerializedName("password_confirmation")
    @Expose
    private List<String> passwordConfirmation = new ArrayList<String>();

    public List<String> getPasswordConfirmation()
    {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(List<String> passwordConfirmation)
    {
        this.passwordConfirmation = passwordConfirmation;
    }


    @SerializedName("current_password")
    @Expose
    private List<String> currentPassword = new ArrayList<String>();

    public List<String> getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(List<String> currentPassword) {
        this.currentPassword = currentPassword;
    }


}