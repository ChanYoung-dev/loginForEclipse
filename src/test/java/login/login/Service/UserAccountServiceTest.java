package login.login.Service;

import login.login.UserAccount.Repository.UserAccountRepository;
import login.login.UserAccount.Service.UserAccountService;
import login.login.UserAccount.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class UserAccountServiceTest {

    @Autowired
    UserAccountService userAccountService;
    @Autowired
    UserAccountRepository userAccountRepository;


    @Commit
    @Test
    void register() {
        userAccountService.register("emrhssla104", "em891389131", "Kim Chan Yeong", "emrhssla@gmail.com");
        UserAccount userAccount = userAccountRepository.findUser(2L);
        String userId = userAccount.getUserId();
        String infoUserId = userAccount.getUserInfo().getUserId();
        Assertions.assertThat(infoUserId).isEqualTo("emrhssla104");

    }
}