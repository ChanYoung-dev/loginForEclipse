package login.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberInfo {

    String email;

    String name;


    public MemberInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
