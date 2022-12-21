package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import plmatmaplus.matmapluspl.controller.CourseCartDTO;
import plmatmaplus.matmapluspl.entity.Cart;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CartRepository;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    private CourseRepository courseRepository;

    @Autowired
    public CartService(CartRepository cartRepository,CourseRepository courseRepository) {
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
    }

    public int getCartSize(HttpServletRequest httpServletRequest) {
        if (isNoActiveSession(httpServletRequest)) return 0;
        else if (!areCoursesInCart(httpServletRequest)) return 0;
        return getCoursesInCartSize(httpServletRequest);
    }

    public boolean isNoActiveSession(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("user") == null;
    }

    public void addCourseToUserCart(Integer userId, Long courseId) {
        if (!isCartExist(userId)) createCartForUser(userId);
        if (!isCourseExist(userId,courseId)) addCourse(userId,courseId);
    }

    public List<CourseCartDTO> getCourseCartDTOList(HttpServletRequest request) {
        if (request.getSession().getAttribute("user")==null) return new ArrayList<>();
        else return mapToCourseCartDTOList(getCourses(request));
    }

    private List<Course> getCourses(HttpServletRequest request) {
        return cartRepository.findByUserId(Integer.parseInt(request.getSession().getAttribute("user").toString())).get().getCourses().stream().toList();
    }
    private List<CourseCartDTO> mapToCourseCartDTOList(List<Course> courses) {

        List<CourseCartDTO> courseCartDTOList = new ArrayList<>();
        for (int i = 0; i < courses.size() ; i++) {
            courseCartDTOList.add(mapToCourseCartDT(courses.get(i)));
        }
        return courseCartDTOList;
    }

    private CourseCartDTO mapToCourseCartDT(Course course) {
        return new CourseCartDTO(course.getIdCourses(),
                course.getName(),
                course.getPrice(),
                course.getAdvancement(),
                "/resources/analiza-shop-roz.png");
    }

    private boolean isCourseExist(Integer userId, Long courseId) {
        return cartRepository.findByUserId(userId).get().getCourses().contains(courseRepository.getReferenceById(courseId));
    }

    private void addCourse(Integer userId,Long courseId) {
        Cart cart = cartRepository.findByUserId(userId).get();
        cart.getCourses().add(courseRepository.findByIdCourses(courseId).get());
        cartRepository.save(cart);
    }

    private boolean isCartExist(Integer userId) {
        return  cartRepository.findByUserId(userId).isPresent();
    }

    private void createCartForUser(Integer userId) {
        Cart cart = new Cart(userId);
        cartRepository.save(cart);
    }

    private boolean areCoursesInCart(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().getAttribute("user");
       return cartRepository.findByUserId(Integer.parseInt(httpServletRequest.getSession().getAttribute("user").toString())).isPresent();
    }

    private int getCoursesInCartSize(HttpServletRequest httpServletRequest) {
        return cartRepository.findByUserId(Integer.parseInt(httpServletRequest.getSession().getAttribute("user").toString()))
                .get()
                .getCourses().size();
    }
}
