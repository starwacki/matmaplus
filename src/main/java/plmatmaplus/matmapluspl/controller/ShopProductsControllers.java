package plmatmaplus.matmapluspl.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.CartService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShopProductsControllers {

    private CartService cartService;

    @Autowired
    public ShopProductsControllers(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/matmaplus/shop/analizapodstawa")
    public String baseMathAnalysisPage(Model model, HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "analiza-matematyczna.html";
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "analiza-matematyczna-roz.html";
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "kurs-matura-podstawowa.html";
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "kurs-matura-rozszerzona.html";
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "egzamin-ósmioklasisty.html";
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage() {
        return "całki-na-studiach.html";
    }
}
