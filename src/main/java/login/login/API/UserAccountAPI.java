package login.login.API;

import login.login.Exception.NoSignUpException;
import login.login.Exception.NoUserException;
import login.login.domain.UserInfo;
import login.login.dto.RequestInfoSignUp;
import login.login.dto.RequestUserName;
import login.login.dto.ResponseUserName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserAccountAPI {

    @Value("${msa.memberAPI}")
    String serverMemberAPI;


    @Transactional
    public String requestName(String userId) {

        RequestUserName dto = new RequestUserName(userId);


        WebClient webClient = WebClient.builder().baseUrl(serverMemberAPI).build();

        ResponseUserName result = webClient.get()
                .uri("/member/{userId}/name", userId)
                .header(HttpHeaders.SET_COOKIE,"JSESSIONID","")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new NoUserException("해당 유저는 없습니다")))
                .bodyToMono(ResponseUserName.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(ExceptionControl::ConnectionError, ex -> new NoUserException("연결시간초과", ex))
                .block();
        System.out.println("result.getName() = " + result.getName());

        return result.getName();
    }

    @Transactional
    public UserInfo requestSignUp(String userId, String userName, String email) {

        RequestInfoSignUp dto = new RequestInfoSignUp(userId, userName, email);


        WebClient webClient = WebClient.builder().baseUrl(serverMemberAPI).build();

        UserInfo result = webClient.post()
                .uri("/member/register", userId)
                .body(Mono.just(dto), RequestInfoSignUp.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new NoSignUpException("해당 유저는 없습니다")))
                .bodyToMono(UserInfo.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(ExceptionControl::ConnectionError, ex -> new NoUserException("연결시간초과", ex))
                .block();
        System.out.println("result.getName() = " + result.getUserName());

        return result;
    }
}
