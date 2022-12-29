package plmatmaplus.matmapluspl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {



    @PostMapping("/matmaplus/cart/payment")
    public String handlePayment(@RequestParam(name = "orderTotal") String orderTotal, Model model) {
        System.out.println(orderTotal);
        model.addAttribute("orderTotal",orderTotal);
        return "payment.html";
    }

    @GetMapping("/matmaplus/cart/payment")
    public String showRestrictedPage() {
        return "redirect:/matmaplus/cart";
    }

    @PostMapping("/matmaplus/cart/payment/card")
    public String payCart() {
        return "redirect:/matmaplus/shop";
    }

    @PostMapping("/matmaplus/cart/payment/blik")
    public String payBlik() {
        System.out.println("blik");
        return "redirect:/matmaplus/shop";
    }
}


