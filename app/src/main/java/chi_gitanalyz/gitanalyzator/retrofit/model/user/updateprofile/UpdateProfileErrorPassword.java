package chi_gitanalyz.gitanalyzator.retrofit.model.user.updateprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 22.09.2016.
 */

public class UpdateProfileErrorPassword
{
        @SerializedName("error")
        @Expose
        private Error error;

        public Error getError()
        {
            return error;
        }

        public void setError(Error error)
        {
            this.error = error;
        }
}
