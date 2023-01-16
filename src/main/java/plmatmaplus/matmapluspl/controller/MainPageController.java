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
        return "shop.html";
    }

    @RequestMapping("/matmaplus")
    public String mainView() {
        return "mainview.html";
    }

    @RequestMapping("/matmaplus/blog")
    public String blog() {
        return "blog.html";
    }

    @RequestMapping("/matmaplus/contact")
    public String contact() {
        return "contact.html";
    }

}
