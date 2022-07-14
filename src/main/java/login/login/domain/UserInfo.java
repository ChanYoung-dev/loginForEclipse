package login.login.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Table(name = "tbl_user_info")
@Getter
@Setter
@Entity
public class UserInfo {

	@Id
	@Column(name = "user_id")
	private String userId;
	
	private String userName;
	
	private String mail;

	@OneToOne(mappedBy = "userInfo", fetch = FetchType.LAZY)
	private UserAccount userAccount;

	public static UserInfo createUserInfo(String userId, String userName, String mail) {
		UserInfo userInfo = new UserInfo();
		userInfo.userId = userId;
		userInfo.userName = userName;
		userInfo.mail= mail;
		return userInfo;
	}
}
