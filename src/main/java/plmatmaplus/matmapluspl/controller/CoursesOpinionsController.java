package plmatmaplus.matmapluspl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plmatmaplus.matmapluspl.service.CourseOpinionsService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CoursesOpinionsController {

    private final CourseOpinionsService courseOpinionsService;

    @Autowired
    CoursesOpinionsController(CourseOpinionsService courseOpinionsService) {
        this.courseOpinionsService = courseOpinionsService;
    }

    @PostMapping("/matmaplus/comment")
    public String comment(@RequestParam(defaultValue = "0") int rating,
                          @RequestParam int courseId,
                          HttpServletRequest request) {
        return "mainview.html";
    }

}
