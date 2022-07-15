package login.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequestDto {
    String id;
    String password;
    boolean error = false;

    public LoginRequestDto(String id, String password, boolean error) {
        this.id = id;
        this.password = password;
        this.error = error;
    }
}
