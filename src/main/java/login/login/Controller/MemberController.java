package login.login.Controller;


import login.login.API.UserInfoAPI;
import login.login.LoginAnnotation.Login;
import login.login.Repository.UserInfoRepository;
import login.login.Service.UserInfoService;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import login.login.dto.MemberInfo;
import login.login.dto.RequestInfoSignUp;
import login.login.dto.ResponseUserId;
import login.login.dto.ResponseUserName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final UserInfoRepository userInfoRepository;

    private final UserInfoService userInfoService;
    private final UserInfoAPI userInfoAPI;
    @Value("${msa.domain}")
    String serverDomain;

    //Join방식
    //@GetMapping("/my-page")
    public String info(@Login UserAccount userAccount, HttpServletResponse response, Model model) {
        UserInfo userinfo = userInfoRepository.findUserById(userAccount.getUserId());
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        model.addAttribute("serverDomain", serverDomain);
        model.addAttribute(memberInfo);
        return "my-page";
    }

    @GetMapping("/my-page")
    public String infoByMSA(Model model) {
        System.out.println("시작");
        String userId = userInfoAPI.requestId();
        System.out.println("userId = " + userId);
        UserInfo userinfo = userInfoRepository.findUserById(userId);
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        model.addAttribute("serverDomain", serverDomain);
        model.addAttribute(memberInfo);
        return "my-page";
    }

    @GetMapping("/{userId}/my-page")
    public String infoByMSA2(@PathVariable String userId, Model model) {
        UserInfo userinfo = userInfoRepository.findUserById(userId);
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        model.addAttribute("serverDomain", serverDomain);
        model.addAttribute(memberInfo);
        return "my-page";
    }

    @GetMapping("/{userId}/name")
    @ResponseBody
    public ResponseEntity<ResponseUserName> findUser(@PathVariable String userId){
        UserInfo userInfo = userInfoRepository.findUserById(userId);

        ResponseUserName responseUserName = new ResponseUserName(userInfo.getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(responseUserName);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<UserInfo> registerInfo(@RequestBody RequestInfoSignUp dto){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RequestInfoSignUp infoDto = mapper.map(dto, RequestInfoSignUp.class);

        UserInfo userInfo = userInfoService.register(infoDto.getUserId(), infoDto.getUserName(), infoDto.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

}
