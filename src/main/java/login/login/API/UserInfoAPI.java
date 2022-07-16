package login.login.API;

import login.login.Exception.NoUserException;
import login.login.dto.RequestUserName;
import login.login.dto.ResponseUserId;
import login.login.dto.ResponseUserName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserInfoAPI {

    @Value("${msa.accountAPI}")
    String serverAccountAPI;


    @Transactional
    public String requestId() {

        WebClient webClient = WebClient.builder().baseUrl(serverAccountAPI).build();

        ResponseUserId result = webClient.get()
                .uri("/auth/user")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new NoUserException("로그인을 해주세요")))
                .bodyToMono(ResponseUserId.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(ExceptionControl::ConnectionError, ex -> new NoUserException("연결시간초과", ex))
                .block();

        return result.getUserId();
    }
}
