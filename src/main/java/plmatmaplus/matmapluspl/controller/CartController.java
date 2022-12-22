package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.dto.CourseCartDTO;
import plmatmaplus.matmapluspl.dto.OrderDTO;
import plmatmaplus.matmapluspl.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/matmaplus/cart")
    public String cart(
                       Model model, HttpServletRequest request) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrder(coursesInCart);
        model.addAttribute("courses", coursesInCart);
        model.addAttribute("order",orderDTO);
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "cart.html";
    }

    @PostMapping("/matmaplus/cart")
    public String cart(@RequestParam(name="code") String code,
                       Model model, HttpServletRequest request) {
      if (cartService.isPromoCodeExist(code))
          return cartWithPromoCode(request,code,model);
      else
          return cartWithWrongPromoCode(request,model);
    }


    @PostMapping("/cart/addToCart")
    public String addCourseToCart(@RequestParam("productId")Long productId, HttpServletRequest request) {
        if (cartService.isNoActiveSession(request))
            return "redirect:/matmaplus/login?mustlogin";
        else
            cartService.addCourseToUserCart(Integer.parseInt(request.getSession().getAttribute("user").toString()),productId);
        return "redirect:/matmaplus/shop";
    }

    private String cartWithPromoCode( HttpServletRequest request,String code,Model model) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrder(coursesInCart,cartService.getPromoCode(code));
        model.addAttribute("courses", coursesInCart);
        model.addAttribute("order",orderDTO);
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "cart.html";
    }

    private String cartWithWrongPromoCode(HttpServletRequest request,Model model) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrder(coursesInCart);
        model.addAttribute("courses", coursesInCart);
        model.addAttribute("order",orderDTO);
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "cart.html";
    }
}
