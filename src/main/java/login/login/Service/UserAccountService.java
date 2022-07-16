package login.login.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import login.login.API.UserAccountAPI;
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

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserAccountService {
	
	private final UserAccountRepository userAccountRepository;

	private final UserAccountAPI userAccountAPI;

	@Transactional
	public void register(String userId, String userPassword, String name, String email) {
		UserInfo userInfo = userAccountAPI.requestSignUp(userId, name, email);
		UserAccount userAccount = UserAccount.createUserAccount(userId, userPassword, userInfo);
		userAccountRepository.save(userAccount);
	}


	@Transactional
	public UserAccount login(LoginRequestDto dto) {
		try {
			UserAccount user = userAccountRepository.findByUserId(dto.getId());
			if (!(dto.getPassword().equals(user.getUserPassword()))) {
				throw new LoginException("이메일과 비밀번호가 올바르지 않습니다.");
			}
			user.updateLoginYN("Y");
			userAccountRepository.save(user);
			return user;
		}
		catch (NoResultException e){
			throw new LoginException("이메일과 비밀번호가 올바르지 않습니다.");
		}
	}

	@Transactional
	public void controlLoginYN(UserAccount userAccount){
		userAccount.updateLoginYN("N");
		userAccountRepository.save(userAccount);
		System.out.println("Logyn = " + userAccount.getLoginYN());
	}

	@Transactional
	public boolean isIdDuplicated(String id) {
		String regExpression = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*$";

		if (!Pattern.matches(regExpression, id)) {
			throw new RuntimeException("아이디가 올바르지 않습니다.");
		}

		return userAccountRepository.existsById(id);
	}




}
