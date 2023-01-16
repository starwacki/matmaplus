package plmatmaplus.matmapluspl.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.service.UserRegisterService;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final UserRegisterService userRegisterService;

    @RequestMapping("/matmaplus/register")
    public String register(Model model) {
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", user);
        return Views.REGISTER_VIEW.toString();
    }


    @PostMapping("matmaplus/register/save")
    public String registration(@ModelAttribute("user") UserRegisterDTO userDto){
        if (userRegisterService.isUserExist(userDto)) {
            return RedirectViews.WRONG_USERNAME_REGISTER_VIEW.toString();
        } else if (userRegisterService.isEmailTaken(userDto)) {
            return RedirectViews.WRONG_EMAIL_REGISTER_VIEW.toString();
        } else if (!userRegisterService.isPasswordLengthProperty(userDto)) {
            return RedirectViews.WRONG_PASSWORD_LENGTH_REGISTER_VIEW.toString();
        } else if (!userRegisterService.isPasswordSame(userDto)) {
            return RedirectViews.WRONG_PASSWORD_REGISTER_VIEW.toString();
        } else {
            userRegisterService.registerUser(userDto);
            return  RedirectViews.SUCCESS_REGISTER_VIEW.toString();
        }
    }

    }



