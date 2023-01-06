package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.service.CartService;
import plmatmaplus.matmapluspl.service.CourseOpinionsService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CoursesOpinionsController {

    private final CourseOpinionsService courseOpinionsService;

    private final CartService cartService;

    @Autowired
    CoursesOpinionsController(CourseOpinionsService courseOpinionsService, CartService cartService) {
        this.courseOpinionsService = courseOpinionsService;
        this.cartService = cartService;
    }

    @PostMapping("/matmaplus/comment")
    public String comment(@RequestParam(defaultValue = "0") int rating,
                          @RequestParam int courseId,
                          @RequestParam String comment,
                          HttpServletRequest request) {
        if (!courseOpinionsService.isUserExist(request))
            return RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        else if (courseOpinionsService.isUserRatedCourse(request,courseId))
            return courseOpinionsService.getRedirectViewByCourseId(courseId) + "?rated";
        else if (!courseOpinionsService.isOpinionPicked(rating))
            return courseOpinionsService.getRedirectViewByCourseId(courseId) + "?opinionPicked";
        else
            courseOpinionsService.addCommentToCourse(courseId, comment, request, rating);
            return courseOpinionsService.getRedirectViewByCourseId(courseId);
    }

    @RequestMapping("/matmaplus/shop/analizapodstawa")
    public String baseMathAnalysisPage(Model model, HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.BASE_MATH_ANALYSIS));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.BASE_MATH_ANALYSIS));
        return Views.ANALIZA_MATEMATYCZNA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.EXTENDED_MATH_ANALYSIS));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.EXTENDED_MATH_ANALYSIS));
        System.out.println(courseOpinionsService.getCourseOpinionSectionDTO(CourseID.EXTENDED_MATH_ANALYSIS).toString());
        return Views.ANALIZA_MATEMATYCZNA_ROZ_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.BASE_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.BASE_EXAM));
        return Views.KURS_MATURA_PODST_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.EXTENDED_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.EXTENDED_EXAM));
        return Views.KURS_MATURA_ROZ_VIEW.toString();
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.PRIMARY_SCHOOL_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.PRIMARY_SCHOOL_EXAM));
        return Views.EGZAMIN_ÓSMOKLASISTY_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.INTEGRALS));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.INTEGRALS));
        return Views.CAŁKI_NA_STUDIACH_VIEW.toString();
    }

}
