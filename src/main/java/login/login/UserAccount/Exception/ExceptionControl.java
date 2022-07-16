package login.login.UserAccount.Exception;

import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.concurrent.TimeoutException;

public class ExceptionControl {

    protected static boolean ConnectionError(Throwable throwable) {
        return throwable instanceof TimeoutException || throwable instanceof WebClientRequestException;
    }
}
