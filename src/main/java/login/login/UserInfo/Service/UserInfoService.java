package login.login.UserInfo.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import login.login.UserInfo.Repository.UserInfoRepository;
import login.login.UserInfo.domain.UserInfo;
import lombok.RequiredArgsConstructor;

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
