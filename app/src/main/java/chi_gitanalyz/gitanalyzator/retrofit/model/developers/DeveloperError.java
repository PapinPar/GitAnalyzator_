package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 22.09.2016.
 */

public class DeveloperError {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("error")
        @Expose
        private String error;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
}
