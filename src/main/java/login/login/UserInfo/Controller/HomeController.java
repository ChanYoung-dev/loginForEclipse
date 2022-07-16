package login.login.UserInfo.Controller;

import login.login.UserAccount.API.UserAccountAPI;
import login.login.UserAccount.LoginAnnotation.Login;
import login.login.UserAccount.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserAccountAPI userAccountAPI;

    @GetMapping("/")
    public String home(@Login UserAccount loginMember, Model
            model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home-guest";
        }

        //이름찾기
        String userName = userAccountAPI.requestName(loginMember.getUserId());
        model.addAttribute("memberId", loginMember.getUserId());
        model.addAttribute("memberName", userName);
        return "home-member";
    }
}
