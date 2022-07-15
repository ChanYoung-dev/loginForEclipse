package login.login.Controller;


import login.login.LoginAnnotation.Login;
import login.login.Repository.UserInfoRepository;
import login.login.domain.UserAccount;
import login.login.domain.UserInfo;
import login.login.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final UserInfoRepository userInfoRepository;

    @Value("${msa.domain}")
    String serverDomain;

    //Join방식
    @GetMapping("/my-page")
    public String info(@Login UserAccount userAccount, HttpServletResponse response, Model model) {
        UserInfo userinfo = userInfoRepository.findMember(userAccount.getUserInfo().getUserId());
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        System.out.println("serverDomain"+serverDomain);
        model.addAttribute("serverDomain", serverDomain);
        model.addAttribute(memberInfo);
        return "my-page";
    }
}
