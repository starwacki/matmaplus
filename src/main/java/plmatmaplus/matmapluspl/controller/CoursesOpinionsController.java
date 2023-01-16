package plmatmaplus.matmapluspl.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.service.CartService;
import plmatmaplus.matmapluspl.service.CourseOpinionsService;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class CoursesOpinionsController {

    private final CourseOpinionsService courseOpinionsService;

    private final CartService cartService;


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
        return Views.BASE_MATH_ANALYSIS_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/analizarozszerzona")
    public String extendedMathAnalysisPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.EXTENDED_MATH_ANALYSIS));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.EXTENDED_MATH_ANALYSIS));
        return Views.EXTENDED_MATH_ANALYSIS_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturapodstawowa")
    public String baseExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.BASE_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.BASE_EXAM));
        return Views.BASE_EXAM_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/maturarozszerzona")
    public String extendedExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.EXTENDED_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.EXTENDED_EXAM));
        return Views.EXTENDED_EXAM_VIEW.toString();
    }


    @RequestMapping("/matmaplus/shop/egzamin-ósmioklasisty")
    public String primarySchoolExamPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.PRIMARY_SCHOOL_EXAM));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.PRIMARY_SCHOOL_EXAM));
        return Views.PRIMARY_SCHOOL_EXAM_VIEW.toString();
    }

    @RequestMapping("/matmaplus/shop/całki")
    public String integralsPage(Model model,HttpServletRequest request) {
        model.addAttribute("cartItems",cartService.getCartSize(request));
        model.addAttribute("comments",courseOpinionsService.getCourseComments(CourseID.INTEGRALS));
        model.addAttribute("opinion",courseOpinionsService.getCourseOpinionSectionDTO(CourseID.INTEGRALS));
        return Views.INTEGRALS_VIEW.toString();
    }

}
