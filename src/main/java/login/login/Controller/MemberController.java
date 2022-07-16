package login.login.Controller;


import login.login.API.UserInfoAPI;
import login.login.LoginAnnotation.Login;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import login.login.domain.UserInfoDto;
import login.login.dto.MemberInfo;
import login.login.dto.ResponseUserName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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
    private final UserInfoAPI userInfoAPI;
    @Value("${msa.domain}")
    String serverDomain;

    //Join방식
    @GetMapping("/my-page")
    public String info(@Login UserAccount userAccount, HttpServletResponse response, Model model) {
        UserInfo userinfo = userInfoRepository.findUserById(userAccount.getUserId());
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        model.addAttribute("serverDomain", serverDomain);
        model.addAttribute(memberInfo);
        return "my-page";
    }

//    @GetMapping("/my-page")
//    public String infoByMSA(Model model) {
//        System.out.println("시작");
//        String userId = userInfoAPI.requestId();
//        System.out.println("userId = " + userId);
//        UserInfo userinfo = userInfoRepository.findUserById(userId);
//        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
//        model.addAttribute("serverDomain", serverDomain);
//        model.addAttribute(memberInfo);
//        return "my-page";
//    }

    @GetMapping("/name/{userId}")
    @ResponseBody
    public ResponseEntity<ResponseUserName> findUser(@PathVariable String userId){
        UserInfo userInfo = userInfoRepository.findUserById(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserInfoDto userInfoDto = mapper.map(userInfo, UserInfoDto.class);
        System.out.println("userInfoDto = " + userInfoDto);

        ResponseUserName responseUserName = new ResponseUserName(userInfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(responseUserName);
    }
}
