package plmatmaplus.matmapluspl.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.UserCoursesService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class UserCoursesController {

    private  final UserCoursesService userCoursesService;

    @RequestMapping("/user/courses")
    public String userCourses(HttpServletRequest request, Model model) {
        if (userCoursesService.isNoActiveSession(request))
            return RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        else
            model.addAttribute("courses", userCoursesService.mapToUserCoursesDTOList(request));
            return Views.USER_COURSES_VIEW.toString();
    }


}
