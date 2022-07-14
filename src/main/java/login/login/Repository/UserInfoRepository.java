package login.login.Repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInfoRepository {

	private final EntityManager em;

	public UserInfo findUser(Long id) {
		return em.find(UserInfo.class, id);
	}
	
	public void save(UserInfo userInfo) {
		em.persist(userInfo);
	}
}
