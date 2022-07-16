package login.login.UserAccount.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class ResponseUserName {
    String name;

    public ResponseUserName() {
    }

    public ResponseUserName(String name) {
        this.name = name;
    }
}
