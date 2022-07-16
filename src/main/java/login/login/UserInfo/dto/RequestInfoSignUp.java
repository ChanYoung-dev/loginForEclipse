package login.login.UserInfo.dto;

import lombok.Getter;

@Getter
public class RequestInfoSignUp {

    String userId;
    String userName;
    String email;

    public RequestInfoSignUp(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
}
