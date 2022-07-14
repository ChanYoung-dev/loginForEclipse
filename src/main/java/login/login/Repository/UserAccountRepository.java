package login.login.Repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserAccountRepository {

	private final EntityManager em;
	
	public void save(UserAccount userAccount) {
		em.persist(userAccount);
	}

	public UserAccount findByUserId(String userId){
		return em.createQuery("select u from UserAccount u where u.userInfo.id = :userId", UserAccount.class).setParameter("userId", userId).getSingleResult();
	}
}
