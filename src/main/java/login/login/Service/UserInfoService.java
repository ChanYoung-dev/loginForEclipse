package login.login.Service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import login.login.Repository.UserInfoRepository;
import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;

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
}
