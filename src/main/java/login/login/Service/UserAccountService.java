package login.login.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import login.login.Exception.LoginException;
import login.login.dto.LoginRequestDto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import login.login.Repository.UserAccountRepository;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {
	
	private final UserAccountRepository userAccountRepository;
	private final UserInfoRepository userInfoRepository;

	@Transactional
	public void register(String userId, String userPassword, String name, String mail) {
		UserInfo userInfo = UserInfo.createUserInfo(userId, name, mail);
		UserAccount userAccount = UserAccount.createUserAccount(userPassword, userInfo);
		userInfoRepository.save(userInfo);
		userAccountRepository.save(userAccount);
	}


	@Transactional
	public UserAccount login(LoginRequestDto dto) {
		try {
			UserAccount user = userAccountRepository.findByUserId(dto.getId());
			if (!(dto.getPassword().equals(user.getUserPassword()))) {
				throw new LoginException("이메일과 비밀번호가 올바르지 않습니다.");
			}
			return user;
		}
		catch (NoResultException e){
			throw new LoginException("이메일과 비밀번호가 올바르지 않습니다.");
		}

	}



}
