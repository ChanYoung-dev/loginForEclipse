package login.login.Controller;


import login.login.Repository.UserInfoRepository;
import login.login.domain.UserInfo;
import login.login.dto.LoginRequestDto;
import login.login.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    
    private final UserInfoRepository userInfoRepository;

    //Join방식
    @GetMapping("/my-page")
    public String info(HttpServletResponse response, Model model) {
        UserInfo userinfo = userInfoRepository.findMember("emrhssla");
        MemberInfo memberInfo = new MemberInfo(userinfo.getEmail(), userinfo.getUserName());
        model.addAttribute(memberInfo);
        return "my-page";
    }
}
