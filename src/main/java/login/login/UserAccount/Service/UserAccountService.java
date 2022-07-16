package login.login.UserAccount.Service;

import javax.persistence.NoResultException;

import login.login.UserAccount.API.UserAccountAPI;
import login.login.UserAccount.Exception.LoginException;
import login.login.UserAccount.dto.LoginRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import login.login.UserAccount.Repository.UserAccountRepository;
import login.login.UserAccount.domain.UserAccount;
import login.login.UserInfo.domain.UserInfo;
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
		catch (Exception e){
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
