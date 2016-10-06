package chi_gitanalyz.gitanalyzator.retrofit.model.response.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 22.09.2016.
 */

public class UpdateProfileErrorPassword
{
        @SerializedName("error")
        @Expose
        private ProfileError error;

        public ProfileError getError()
        {
            return error;
        }

        public void setError(ProfileError error)
        {
            this.error = error;
        }
}
