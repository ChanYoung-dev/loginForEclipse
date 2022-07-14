package login.login.Controller;

import login.login.Exception.LoginException;
import login.login.Repository.UserAccountRepository;
import login.login.domain.UserAccount;
import login.login.dto.LoginRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import login.login.Service.UserAccountService;
import login.login.Service.UserInfoService;
//import login.domain.UserAccount;
import login.login.domain.UserInfo;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final UserInfoService userInfoService;
	private final UserAccountService userAccountService;
	private final UserAccountRepository userAccountRepository;

	
	@GetMapping
	@ResponseBody
	public String home() {
		return "home";
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		userAccountService.register("emrhssla","em89138913", "chanyoung", "emrhssla@gmail.com");
		return "success";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public String test2() {
		UserAccount emrhssla = userAccountRepository.findByUserId("emrhssla");
		System.out.println("emrhssla = " + emrhssla);
		return "success";
	}

	@GetMapping("/login")
	public String loginForm(Model model) {
		return "loginForm";
	}

	@PostMapping("/login")
	public ResponseEntity test3(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
		userAccountService.login(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ExceptionHandler
	public ResponseEntity loginExceptionHandler(LoginException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	

}
