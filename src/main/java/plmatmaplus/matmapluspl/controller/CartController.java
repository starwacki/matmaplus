package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.service.CartService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/addToCart")
    public String addCourseToCart(@RequestParam("productId")Long productId, HttpServletRequest request) {
        if (cartService.isNoActiveSession(request))
            return "redirect:/matmaplus/login?mustlogin";
        else
            cartService.addCourseToUserCart(Integer.parseInt(request.getSession().getAttribute("user").toString()),productId);
        return "redirect:/matmaplus/shop";
    }
}
