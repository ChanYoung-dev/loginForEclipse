package login.login.UserInfo.Exception;

import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.concurrent.TimeoutException;

public class ExceptionControl {

    public static boolean ConnectionError(Throwable throwable) {
        return throwable instanceof TimeoutException || throwable instanceof WebClientRequestException;
    }
}
