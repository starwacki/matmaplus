package plmatmaplus.matmapluspl.controller.templatescontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.service.CartService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainPageController {

    private CartService cartService;
    @Autowired
    public MainPageController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/matmaplus")
    public String mainView() {
        return "mainview.html";
    }

    @RequestMapping("/matmaplus/blog")
    public String blog() {
        return "blog.html";
    }

    @RequestMapping("/matmaplus/shop")
    public String shop(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "shop.html";
    }

    @RequestMapping("/matmaplus/register")
    public String register(Model model) {
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", user);
        return "register.html";
    }

    @RequestMapping("/matmaplus/contact")
    public String contact() {
        return "contact.html";
    }

    @RequestMapping("/user/courses")
    public String userCourses(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("user")==null)
            return "redirect:/matmaplus/login?mustlogin";
        return "usercourses.html";
    }

    @RequestMapping("/matmaplus/login")
    public String login(Model model) {
        UserLoginDTO user = new UserLoginDTO();
        model.addAttribute("userLoginDTO", user);
        return "login.html";
    }
}
