package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.service.UserLoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private UserLoginService userLoginService;

    @Autowired
    public LoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping("/matmaplus/loginuser")
    public String login(@ModelAttribute("user")UserLoginDTO userLoginDTO,  HttpServletRequest request) {
        if (!userLoginService.isUserExist(userLoginDTO)) {
            return "redirect:/matmaplus/login?usererror";
        }
        else if (!userLoginService.isPasswordProperty(userLoginDTO)) {
            return "redirect:/matmaplus/login?usererror";
        }
        HttpSession session = request.getSession();
        UserEntity user = userLoginService.getExistUser(userLoginDTO);
        session.setAttribute("user", user.getIdUsers());
        return "redirect:/matmaplus/login?succes";
    }




}
