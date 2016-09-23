package chi_gitanalyz.gitanalyzator.db.sqlite.model;

/**
 * Created by Papin on 23.09.2016.
 */

public class DbError {
    private String message;
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
