package login.login.Service;

import login.login.Repository.UserAccountRepository;
import login.login.domain.UserAccount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserAccountServiceTest {

    @Autowired UserAccountService userAccountService;
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