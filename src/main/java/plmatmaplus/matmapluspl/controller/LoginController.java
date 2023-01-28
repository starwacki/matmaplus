package plmatmaplus.matmapluspl.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.service.UserLoginService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @RequestMapping("/matmaplus/login")
    public String login(Model model) {
        UserLoginDTO user = new UserLoginDTO();
        model.addAttribute("userLoginDTO", user);
        return Views.LOGIN_VIEW.toString();
    }

    @RequestMapping("/matmaplus/logout")
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return RedirectViews.LOGOUT.toString();
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