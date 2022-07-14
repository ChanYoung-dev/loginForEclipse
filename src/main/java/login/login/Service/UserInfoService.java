package login.login.Service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import login.login.Repository.UserInfoRepository;
import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserInfoService {
	
	private final UserInfoRepository userInfoRepository;
	/*
	@Transactional
	public void save() {
		userInfoRepository.save(new UserInfo("chan", "123456", "emrhssla@gmail.com"));
	}
	*/

	@Transactional
	public boolean isEmailDuplicated(String email) {
		String regExpression = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{2,5}$";

		if (!Pattern.matches(regExpression, email)) {
			throw new RuntimeException("이메일이 올바르지 않습니다.");
		}

		return userInfoRepository.existsByEmail(email);
	}

	@Transactional
	public boolean isIdDuplicated(String id) {
		String regExpression = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*$";

		if (!Pattern.matches(regExpression, id)) {
			throw new RuntimeException("아이디가 올바르지 않습니다.");
		}

		return userInfoRepository.existsById(id);
	}
}
