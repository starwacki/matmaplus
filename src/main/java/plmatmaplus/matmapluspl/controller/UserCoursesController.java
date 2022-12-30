package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import plmatmaplus.matmapluspl.dto.CourseIMGLinks;
import plmatmaplus.matmapluspl.dto.UserCoursesDTO;
import plmatmaplus.matmapluspl.service.UserCoursesService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserCoursesController {

    private  final UserCoursesService userCoursesService;

    @Autowired
    UserCoursesController(UserCoursesService userCoursesService) {
        this.userCoursesService = userCoursesService;
    }

    @RequestMapping("/user/courses")
    public String userCourses(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("user")==null)
            return "redirect:/matmaplus/login?mustlogin";
        else
            model.addAttribute("courses", userCoursesService.mapToUserCoursesDTOList(request));
            return "usercourses.html";

    }


}
