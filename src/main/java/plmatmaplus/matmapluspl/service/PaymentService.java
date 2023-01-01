package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import plmatmaplus.matmapluspl.entity.Cart;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CartRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Status;
import java.util.List;


@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private TransactionTemplate transactionTemplate;

    private EntityManager entityManager;

    @Autowired
    PaymentService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;

    }

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

    private List<Course> getCoursesInCart(int userId) {
        return cartRepository.findByUserId(userId).get().getCourses();
    }

    private Integer getUserId(HttpServletRequest request) {
        return Integer.parseInt(request.getSession().getAttribute("user").toString());
    }



}
