package login.login.Controller;

import login.login.Exception.LoginException;
import login.login.LoginAnnotation.Login;
import login.login.Repository.UserAccountRepository;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import login.login.domain.UserInfoDto;
import login.login.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/auth")
public class AuthController {

	@Value("${msa.domain}")
	String serverDomain;

	@Value("${msa.auth}")
	String serverAuth;

	@Value("${msa.member}")
	String serverMember;

	private final UserInfoService userInfoService;
	private final UserInfoRepository userInfoRepository;
	private final UserAccountService userAccountService;
	private final UserAccountRepository userAccountRepository;



	@GetMapping("/test")
	@ResponseBody
	public String test() {
		UserAccount userAccount = userAccountRepository.findUser(1L);
		//UserInfo userinfo = userInfoRepository.findMember(loginMember.getUserInfo().getEmail());
		//UserInfo userinfo = userInfoRepository.findMember(userAccount.getUserInfo().getUserId());
		//System.out.println("userAccount.getUserInfo().getUserId() = " + userAccount.get;
		return "hi";
	}

	@GetMapping("/login")
	public String loginForm(@Login UserAccount userAccount, Model model, @RequestParam(required = false) String redirectURL) {
		if(userAccount != null){
			return "redirect:/";
		}
		model.addAttribute("serverDomain", serverDomain);
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

	@GetMapping("/sign-up")
	public String signUpForm(Model model){
		model.addAttribute("serverAuth",serverAuth);
		return "auth/signUpForm";
	}

	@PostMapping("/sign-up")
	public ResponseEntity signUp(@RequestBody SignUpRequestDto dto){
		userAccountService.register(dto.getId(), dto.getPassword(), dto.getName(), dto.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity getUserId(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("세션이없다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		UserAccount loginMember = (UserAccount) session.getAttribute("loginMember");
		if (loginMember == null) {
			System.out.println("사용자가없다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ResponseUserId userId = mapper.map(loginMember, ResponseUserId.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(userId);
	}

	@PostMapping("/email-validation")
	public ResponseEntity emailValidation(@RequestBody EmailValidationRequestDto dto) {
		if (userInfoService.isEmailDuplicated(dto.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}

	@PostMapping("/id-validation")
	public ResponseEntity idValidation(@RequestBody IdValidationRequestDto dto) {
		if (userInfoService.isIdDuplicated(dto.getId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
	}

	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity logout(@Login UserAccount loginMember, HttpServletRequest request) {
		System.out.println(" reload....");
		userAccountService.controlLoginYN(loginMember);
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
