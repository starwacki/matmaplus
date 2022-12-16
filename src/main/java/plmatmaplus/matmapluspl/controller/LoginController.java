package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.service.UserLoginService;

@Controller
public class LoginController {

    private UserLoginService userLoginService;

    @Autowired
    public LoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping("/matmaplus/login")
    public void login(@ModelAttribute("user")UserLoginDTO userLoginDTO) {

    }




}
