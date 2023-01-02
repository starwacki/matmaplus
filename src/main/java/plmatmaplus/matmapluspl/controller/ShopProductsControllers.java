package plmatmaplus.matmapluspl.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.CartService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ShopProductsControllers {

    private final CartService cartService;

    @Autowired
    public ShopProductsControllers(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/matmaplus/shop")
    public String shop(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "shop.html";
    }

    @RequestMapping("/matmaplus/shop/analizapodstawa")
    public String baseMathAnalysisPage(Model model, HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.ANALIZA_MATEMATYCZNA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.ANALIZA_MATEMATYCZNA_ROZ_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.KURS_MATURA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.KURS_MATURA_ROZ_VIEW.toString();
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return Views.EGZAMIN_ÓSMOKLASISTY_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage() {
        return Views.CAŁKI_NA_STUDIACH_VIEW.toString();
    }
}
