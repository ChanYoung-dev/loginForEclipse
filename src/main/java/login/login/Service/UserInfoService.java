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

	@Transactional
	public UserInfo register(String userId, String name, String email) {
		UserInfo userInfo = UserInfo.createUserInfo(userId, name, email);
		userInfoRepository.save(userInfo);
		return userInfo;
	}

}
