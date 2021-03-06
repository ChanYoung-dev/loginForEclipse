package login.login.UserAccount.Controller;

import login.login.UserAccount.Exception.LoginException;
import login.login.UserAccount.LoginAnnotation.Login;
import login.login.UserAccount.Repository.UserAccountRepository;
import login.login.UserAccount.domain.ResponseUserId;
import login.login.UserAccount.domain.UserAccount;
import login.login.UserAccount.dto.IdValidationRequestDto;
import login.login.UserAccount.dto.LoginRequestDto;
import login.login.UserAccount.dto.SignUpRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import login.login.UserAccount.Service.UserAccountService;
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

	private final UserAccountService userAccountService;
	private final UserAccountRepository userAccountRepository;



	@GetMapping("/healthcheck")
	@ResponseBody
	public String test() {
		return "auth-service working..";
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
		//????????? ????????? ?????? ?????? ??????
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
			System.out.println("???????????????");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		UserAccount loginMember = (UserAccount) session.getAttribute("loginMember");
		if (loginMember == null) {
			System.out.println("??????????????????");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ResponseUserId userId = mapper.map(loginMember, ResponseUserId.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(userId);
	}

	@PostMapping("/id-validation")
	public ResponseEntity idValidation(@RequestBody IdValidationRequestDto dto) {
		if (userAccountService.isIdDuplicated(dto.getId())) {
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
