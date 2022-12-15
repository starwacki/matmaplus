package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.service.UserRegisterService;

@Controller
public class RegisterController {

    private UserRegisterService userRegisterService;

    @Autowired
    public RegisterController(final UserRegisterService userService) {
        this.userRegisterService = userService;
    }


    @PostMapping("matmaplus/register/save")
    public String registration(@ModelAttribute("user") UserRegisterDTO userDto){
        if (userRegisterService.isUserExist(userDto)) {
            return "redirect:/matmaplus/register?userExist";
        } else if (userRegisterService.isEmailTaken(userDto)) {
            return "redirect:/matmaplus/register?emailTaken";
        } else if (!userRegisterService.isPasswordLengthProperty(userDto)) {
            return "redirect:/matmaplus/register?wrongPasswordLength";
        } else if (userRegisterService.isPasswordSame(userDto)) {
            return "redirect:/matmaplus/register?wrongPassword";
        }
        userRegisterService.save(userDto);
        return  "redirect:/matmaplus/register?success";
    }
}