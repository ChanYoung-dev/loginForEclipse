package login.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberInfo {

    String mail;

    String name;


    public MemberInfo(String mail, String name) {
        this.mail = mail;
        this.name = name;
    }
}
