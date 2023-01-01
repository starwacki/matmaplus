package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        if (cartService.isNoActiveSession(request))
            return RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        else
            return getUserCart(request,model);
    }

    @PostMapping("/matmaplus/cart/remove")
    public String removeFromCart(@RequestParam(name="index") long index,
                  Model model, HttpServletRequest request        ) {
        cartService.removeCourseFromCart(index,request);
        return getUserCart(request,model);
    }

    @PostMapping("/matmaplus/cart")
    public String cartWithPromoCode(@RequestParam(name="code") String code,
                       Model model, HttpServletRequest request) {
      if (cartService.isPromoCodeExist(code))
          return cartWithPromoCode(request,code,model);
      else
          return cartWithWrongPromoCode(request,model);
    }


    @PostMapping("/cart/addToCart")
    public String addCourseToCart(@RequestParam("productId")Long productId, HttpServletRequest request) {
        if (cartService.isNoActiveSession(request))
            return RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        else
            cartService.addCourseToUserCart(request,productId);
        return RedirectViews.SHOP_VIEW.toString();
    }

    private String cartWithPromoCode( HttpServletRequest request,String code,Model model) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrderWithPromoCode(coursesInCart,cartService.getPromoCode(code));
        addCartAttributes(coursesInCart,request,model,orderDTO);
        return Views.CART_VIEW.toString();
    }

    private String cartWithWrongPromoCode(HttpServletRequest request,Model model) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrderWithPromoCode(coursesInCart);
        addCartAttributes(coursesInCart,request,model,orderDTO);
        return RedirectViews.WRONG_PROMO_CODE_CART_VIEW.toString();
    }

    private void addCartAttributes(List<CourseCartDTO> coursesInCart,HttpServletRequest request,
                                   Model model,OrderDTO orderDTO) {
        model.addAttribute("courses", coursesInCart);
        model.addAttribute("order",orderDTO);
        model.addAttribute("cartItems",cartService.getCartSize(request));
    }

    private String getUserCart(HttpServletRequest request, Model model) {
        List<CourseCartDTO> coursesInCart = cartService.getCourseCartDTOList(request);
        OrderDTO orderDTO = cartService.getOrderWithPromoCode(coursesInCart);
        addCartAttributes(coursesInCart,request,model,orderDTO);
        return Views.CART_VIEW.toString();
    }
}
