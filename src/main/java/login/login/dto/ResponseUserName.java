package login.login.dto;

import lombok.Getter;

@Getter
public class ResponseUserName {
    String name;

    public ResponseUserName(String name) {
        this.name = name;
    }
}
