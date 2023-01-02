package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/matmaplus/contact")
    public String contact() {
        return "contact.html";
    }

}
