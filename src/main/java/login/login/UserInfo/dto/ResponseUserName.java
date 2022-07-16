package login.login.UserInfo.dto;

import lombok.Data;

@Data
public class ResponseUserName {
    String name;

    public ResponseUserName() {
    }

    public ResponseUserName(String name) {
        this.name = name;
    }
}
