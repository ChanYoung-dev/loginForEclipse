package login.login.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Data
public class UserInfoDto {


    private String userId;

    private String userName;


}
