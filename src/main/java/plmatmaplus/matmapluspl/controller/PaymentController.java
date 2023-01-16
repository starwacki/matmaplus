package plmatmaplus.matmapluspl.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.service.PaymentService;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/matmaplus/cart/payment")
    public String handlePayment(@RequestParam(name = "orderTotal") String orderTotal, Model model) {
        model.addAttribute("orderTotal",orderTotal);
        return Views.PAYMENT_VIEW.toString();
    }

    @GetMapping("/matmaplus/cart/payment")
    public String showRestrictedPage() {
        return RedirectViews.CART_VIEW.toString();
    }

    @PostMapping("/matmaplus/cart/payment/card")
    public String payCart(HttpServletRequest request) {
        paymentService.pay(request);
        return RedirectViews.SHOP_VIEW.toString();
    }

    @PostMapping("/matmaplus/cart/payment/blik")
    public String payBlik(HttpServletRequest request) {
        paymentService.pay(request);
        return RedirectViews.SHOP_VIEW.toString();
    }
}


