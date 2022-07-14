package login.login.Controller;

import login.login.Exception.LoginException;
import login.login.Repository.UserAccountRepository;
import login.login.domain.UserAccount;
import login.login.dto.EmailValidationRequestDto;
import login.login.dto.IdValidationRequestDto;
import login.login.dto.LoginRequestDto;
import login.login.dto.SignUpRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import login.login.Service.UserAccountService;
import login.login.Service.UserInfoService;
//import login.domain.UserAccount;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthController {

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
	public ResponseEntity login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
		userAccountService.login(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/auth/sign-up")
	public String signUpForm(Model model){
		return "auth/signUpForm";
	}

	@PostMapping("/auth/sign-up")
	public ResponseEntity signUp(@RequestBody SignUpRequestDto dto){
		System.out.println("id = " + dto.getId());
		System.out.println("password = " + dto.getPassword());
		System.out.println("name = " + dto.getName());
		System.out.println("dto.getMail() = " + dto.getMail());
		userAccountService.register(dto.getId(), dto.getPassword(), dto.getName(), dto.getMail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/auth/email-validation")
	public ResponseEntity emailValidation(@RequestBody EmailValidationRequestDto dto) {
		if (userInfoService.isEmailDuplicated(dto.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}

	@PostMapping("/auth/id-validation")
	public ResponseEntity idValidation(@RequestBody IdValidationRequestDto dto) {
		if (userInfoService.isIdDuplicated(dto.getId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}

	@ExceptionHandler
	public ResponseEntity loginExceptionHandler(LoginException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}


}
