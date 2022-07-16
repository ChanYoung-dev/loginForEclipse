package login.login;

import login.login.UserAccount.Service.UserAccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(UserAccountService userAccountService) {
		return new TestDataInit(userAccountService);
	}

}
