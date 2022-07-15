package login.login.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "tbl_user_account")
@Getter
@Setter
@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId; // 사용자 식별자
	
	private String userPassword;

	private String loginYN;


	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(
			name = "userId",
			insertable = false,
			updatable = false
	)
	private UserInfo userInfo;

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
		userInfo.setUserAccount(this);
	}



	public static UserAccount createUserAccount(String userPassword, UserInfo userInfo) {
		UserAccount userAccount = new UserAccount();
		userAccount.userPassword= userPassword;
		userAccount.setUserInfo(userInfo);
		return userAccount;
	}



}

