package plmatmaplus.matmapluspl.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainPageController {

    @RequestMapping("/matmaplus")
    public String mainView() {
        return "mainview.html";
    }

    @RequestMapping("/matmaplus/blog")
    public String blog() {
        return "blog.html";
    }

    @RequestMapping("/matmaplus/shop")
    public String sklep() {
        return "shop.html";
    }

    @RequestMapping("/matmaplus/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping("/matmaplus/contact")
    public String contact() {
        return "contact.html";
    }

    //Will be removed with user courses in the future!
    @RequestMapping("/matmaplus/user/courses")
    public String userCourses() {
        return "aboutauthor.html";
    }

}
