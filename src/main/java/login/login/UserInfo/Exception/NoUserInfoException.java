package login.login.UserInfo.Exception;

public class NoUserInfoException extends RuntimeException {
    public NoUserInfoException(String message) {
        super(message);
    }

    public NoUserInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
