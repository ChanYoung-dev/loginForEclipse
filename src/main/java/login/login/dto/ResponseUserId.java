package login.login.dto;

import lombok.Data;

@Data
public class ResponseUserId {
    private String userId; // 사용자 식별자

    public ResponseUserId(String userId) {
        this.userId = userId;
    }

    public ResponseUserId() {
    }
}
