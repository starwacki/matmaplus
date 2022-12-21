package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/matmaplus/cart")
    public String cart(@ModelAttribute("cartCourses")CourseCartDTO courseCartDTO,Model model, HttpServletRequest request) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        model.addAttribute("courses", coursesInCart);
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "cart.html";
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
