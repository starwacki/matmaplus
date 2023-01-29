package plmatmaplus.matmapluspl.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.CartService;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class MainPageController {

    private CartService cartService;

    @RequestMapping("/matmaplus/shop")
    public String shop(Model model, HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.SHOP_VIEW.toString();
    }

    @RequestMapping("/matmaplus")
    public String mainView() {
        return Views.MAIN_VIEW.toString();
    }

    @RequestMapping("/matmaplus/blog")
    public String blog() {
        return Views.BLOG.toString();
    }

    @RequestMapping("/matmaplus/contact")
    public String contact() {
        return Views.CONTACT.toString();
    }

}
