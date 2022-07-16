package login.login.UserInfo.Repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import login.login.UserInfo.domain.UserInfo;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserInfoRepository {

	private final EntityManager em;

	public UserInfo findUserById(String id) {
		return em.find(UserInfo.class, id);
	}
	
	public void save(UserInfo userInfo) {
		em.persist(userInfo);
	}

	public UserInfo findMember(String userId){
		return em.createQuery("select i from UserAccount u join u.userInfo i where u.userInfo.id = :userId", UserInfo.class).setParameter("userId", userId).getSingleResult();
	}

	public Boolean existsByEmail(String email) {
		String qlString = "select case when (count(i) > 0) then true else false end from UserInfo i where i.email = :email";
		return em.createQuery(qlString, Boolean.class).setParameter("email", email).getSingleResult();
	}

	public Boolean existsById(String userId) {
		String qlString = "select case when (count(i) > 0) then true else false end from UserInfo i where i.userId = :userId";
		return em.createQuery(qlString, Boolean.class).setParameter("userId", userId).getSingleResult();
	}


}
