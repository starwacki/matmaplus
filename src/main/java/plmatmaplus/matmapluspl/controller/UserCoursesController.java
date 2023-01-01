package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.service.UserCoursesService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserCoursesController {

    private  final UserCoursesService userCoursesService;

    @Autowired
    UserCoursesController(UserCoursesService userCoursesService) {
        this.userCoursesService = userCoursesService;
    }

    @RequestMapping("/user/courses")
    public String userCourses(HttpServletRequest request, Model model) {
        if (userCoursesService.isSessionExist(request))
            return RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        else
            model.addAttribute("courses", userCoursesService.mapToUserCoursesDTOList(request));
            return Views.USER_COURSES_VIEW.toString();
    }


}
