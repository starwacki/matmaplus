package plmatmaplus.matmapluspl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.CartService;
import plmatmaplus.matmapluspl.service.CourseOpinionsService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ShopProductsControllers {

    private final CartService cartService;

    private final CourseOpinionsService courseOpinionsService;

    @Autowired
    public ShopProductsControllers(CartService cartService, CourseOpinionsService courseOpinionsService) {
        this.cartService = cartService;
        this.courseOpinionsService = courseOpinionsService;
    }

    @RequestMapping("/matmaplus/shop")
    public String shop(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        return "shop.html";
    }

    @RequestMapping("/matmaplus/shop/analizapodstawa")
    public String baseMathAnalysisPage(Model model, HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(1l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(1l));
        return Views.ANALIZA_MATEMATYCZNA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(2l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(2l));
        return Views.ANALIZA_MATEMATYCZNA_ROZ_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(3l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(3l));
        return Views.KURS_MATURA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(4l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(4l));
        return Views.KURS_MATURA_ROZ_VIEW.toString();
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(5l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(5l));
        return Views.EGZAMIN_ÓSMOKLASISTY_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(5l));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(5l));
        return Views.CAŁKI_NA_STUDIACH_VIEW.toString();
    }
}
