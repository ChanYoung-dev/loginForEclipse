package login.login.Controller;

import login.login.API.UserAccountAPI;
import login.login.LoginAnnotation.Login;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserInfoRepository userInfoRepository;
//    private final UserAccountAPI userAccountAPI;

    @GetMapping("/")
    public String home(@Login UserAccount loginMember, Model
            model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home-guest";
        }

        //세션이 유지되면 로그인으로 이동
        UserInfo userinfo = userInfoRepository.findMember(loginMember.getUserId());
        //이름찾기
//        String userName = userAccountAPI.requestName(loginMember.getUserInfo().getUserId());
        model.addAttribute("memberName",userinfo.getUserName());
        return "home-member";
    }
}
