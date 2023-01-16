package plmatmaplus.matmapluspl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CartRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void pay(HttpServletRequest request) {
        addCoursesToUserCourses(getUserId(request));
        removeCoursesFromCart(request);
    }

    private void removeCoursesFromCart(HttpServletRequest request) {
       cartRepository.deleteById(getCartId(request));
    }

    private long getCartId(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request)).get().getId();
    }

    private void addCoursesToUserCourses(int userId) {
        userRepository.findByIdUsers((long) userId).get().getCourses().addAll(getCoursesInCart(userId));
    }

    private Set<Course> getCoursesInCart(int userId) {
        return cartRepository.findByUserId(userId).get().getCourses();
    }

    private Integer getUserId(HttpServletRequest request) {
        return Integer.parseInt(request.getSession().getAttribute("user").toString());
    }



}
