package login.login.API;

import login.login.Exception.NoUserException;
import login.login.dto.RequestUserName;
import login.login.dto.ResponseUserName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class UserAccountAPI {

    @Value("${msa.memberAPI}")
    String serverMemberAPI;

    WebClient webClient = WebClient.builder().baseUrl(serverMemberAPI).build();
    @Transactional
    public String requestName(String userId) {

        RequestUserName dto = new RequestUserName(userId);

        ResponseUserName result = webClient.get()
                .uri("/member/{userId}", userId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new NoUserException("해당 유저는 없습니다")))
                .bodyToMono(ResponseUserName.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(ExceptionControl::ConnectionError, ex -> new NoUserException("연결시간초과"))
                .block();

        return result.getName();
    }
}
