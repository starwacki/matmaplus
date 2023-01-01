package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.service.UserLoginService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final UserLoginService userLoginService;

    @Autowired
    public LoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping("/matmaplus/loginuser")
    public String login(@ModelAttribute("user")UserLoginDTO userLoginDTO,  HttpServletRequest request) {
        if (!userLoginService.isUserExist(userLoginDTO))
            return RedirectViews.WRONG_LOGIN_VIEW.toString();
        else if (!userLoginService.isUserLoginAndPasswordProperty(userLoginDTO))
            return RedirectViews.WRONG_LOGIN_VIEW.toString();
        else
            request.getSession().setAttribute("user", userLoginService.getLoginUserId(userLoginDTO));
            return RedirectViews.SUCCESS_LOGIN_VIEW.toString();
    }

}