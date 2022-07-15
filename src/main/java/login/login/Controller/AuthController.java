package login.login.Controller;

import login.login.Exception.LoginException;
import login.login.LoginAnnotation.Login;
import login.login.Repository.UserAccountRepository;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import login.login.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import login.login.Service.UserAccountService;
import login.login.Service.UserInfoService;
//import login.domain.UserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserInfoService userInfoService;

	private final UserInfoRepository userInfoRepository;
	private final UserAccountService userAccountService;
	private final UserAccountRepository userAccountRepository;

	@GetMapping("/")
	public String home(@Login UserAccount loginMember, Model
			model) {
		//세션에 회원 데이터가 없으면 home
		if (loginMember == null) {
			return "home-guest";
		}

		//세션이 유지되면 로그인으로 이동
		UserInfo userinfo = userInfoRepository.findMember(loginMember.getUserInfo().getUserId());
		model.addAttribute("memberName",userinfo.getUserName());
		return "home-member";
	}

	@PostMapping("/test")
	@ResponseBody
	public ResponseEntity test() {
		System.out.println("success");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/test2")
	@ResponseBody
	public String test2() {
		UserAccount emrhssla = userAccountRepository.findByUserId("emrhssla");
		System.out.println("emrhssla = " + emrhssla);
		return "success";
	}

	@GetMapping("/login")
	public String loginForm(Model model, @RequestParam(required = false) String redirectURL) {
		System.out.println("redirectURL = " + redirectURL);
		model.addAttribute("redirectURL",redirectURL);
		return "loginForm";
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto dto, HttpServletResponse response,
								HttpServletRequest request) {
		UserAccount loginMember = userAccountService.login(dto);
		HttpSession session = request.getSession();
		loginMember.setLoginYN("Y");
		//세션에 로그인 회원 정보 보관
		session.setAttribute("loginMember", loginMember);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/auth/sign-up")
	public String signUpForm(Model model){
		return "auth/signUpForm";
	}

	@PostMapping("/auth/sign-up")
	public ResponseEntity signUp(@RequestBody SignUpRequestDto dto){
		userAccountService.register(dto.getId(), dto.getPassword(), dto.getName(), dto.getEmail());
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


	//@GetMapping("/")
	public String homeLoginV3ArgumentResolver(@Login UserAccount loginMember, Model
			model) {
		//세션에 회원 데이터가 없으면 home
		if (loginMember == null) {
			return "loginForm";
		}

		//세션이 유지되면 로그인으로 이동
		UserInfo userinfo = userInfoRepository.findMember(loginMember.getUserInfo().getUserId());
		MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
		model.addAttribute(memberInfo);
		return "redirect:/member/my-page";
	}

	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity logout(@Login UserAccount loginMember, HttpServletRequest request) {
		loginMember.setLoginYN("N");
		System.out.println(" reload....");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}

	@GetMapping("/logout")
	public String logout2(@Login UserAccount loginMember, HttpServletRequest request) {
		//webclient.post
		loginMember.setLoginYN("N");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "/";
	}

	@ExceptionHandler
	public ResponseEntity loginExceptionHandler(LoginException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}




}
