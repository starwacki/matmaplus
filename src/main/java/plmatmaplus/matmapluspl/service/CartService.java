package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.CourseCartDTO;
import plmatmaplus.matmapluspl.dto.OrderDTO;
import plmatmaplus.matmapluspl.entity.Cart;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CartRepository;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.PromoCodeRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CartService {

    private final static int EMPTY_CART = 0;
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository,CourseRepository courseRepository,PromoCodeRepository promoCodeRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.userRepository = userRepository;
    }

    public int getCartSize(HttpServletRequest httpServletRequest) {
        if (isNoActiveSession(httpServletRequest))
            return EMPTY_CART;
        else if (!areCoursesInCart(httpServletRequest))
            return EMPTY_CART;
        else
            return getCoursesInCartSize(httpServletRequest);
    }

    public boolean isNoActiveSession(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("user") == null;
    }

    public void addCourseToUserCart(HttpServletRequest request, Long courseId) {
        if (!isCartExist(getUserId(request)))
            createCartForUser(getUserId(request));
        if (!isUserHaveCourseInCart(getUserId(request),courseId) && !isUserHaveCourse(getUserId(request),courseId))
            addCourse(getUserId(request),courseId);
    }

    public List<CourseCartDTO> getCourseCartDTOList(HttpServletRequest request) {
        if (isNoActiveSession(request) || !isCartExist(request))
            return getEmptyList();
        else
            return mapToCourseCartDTOList(getCourses(request));
    }

    public OrderDTO getOrderWithPromoCode(List<CourseCartDTO> courseCartDTOList) {
        return new OrderDTO(getCartTotal(courseCartDTOList),
                false,
                0,
                getCartTotal(courseCartDTOList));
    }

    public OrderDTO getOrderWithPromoCode(List<CourseCartDTO> courseCartDTOList, int code) {
        double cartTotal = getCartTotal(courseCartDTOList);
        return new OrderDTO(cartTotal,
                true,
                calculatePromoValue(cartTotal,code),
                calculateEndCartTotal(cartTotal,calculatePromoValue(cartTotal,code)));
    }

    public boolean isPromoCodeExist(String promoCode) {
        return promoCodeRepository.findPromoCodeByCode(promoCode).isPresent();
    }

    public Integer getPromoCode(String code) {
        return promoCodeRepository.findPromoCodeByCode(code).get().getPercentValue();
    }

    public void removeCourseFromCart(long index, HttpServletRequest request) {
        Cart cart = cartRepository.findByUserId(getUserId(request)).get();
        cart.getCourses().remove(courseRepository.findByIdCourses(index).get());
        cartRepository.save(cart);
    }

    private int calculatePromoValue(double cartTotal,int code) {
        return (int) (cartTotal/100*code);
    }

    private double calculateEndCartTotal(double cartTotal, int promoValue) {
        return cartTotal-promoValue;
    }


    private boolean isCartExist(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request)).isPresent();
    }

    private double getCartTotal(List<CourseCartDTO> courseCartDTOList) {
        return courseCartDTOList
                .stream()
                .mapToDouble(courseCartDTO -> courseCartDTO.getPrice()).sum();
    }

    private List<CourseCartDTO> getEmptyList() {
        return new ArrayList<>();
    }

    private Set<Course> getCourses(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request)).get().getCourses();
    }
    private List<CourseCartDTO> mapToCourseCartDTOList(Set<Course> courses) {
      return courses.stream()
              .map(course -> mapToCourseCartDT(course))
              .sorted(Comparator.comparing(CourseCartDTO::getIdCourses))
              .toList();
    }

    private CourseCartDTO mapToCourseCartDT(Course course) {
        return new CourseCartDTO(course.getIdCourses(),
                course.getName(),
                course.getPrice(),
                course.getAdvancement(),
                course.getDetails().getImgLink());
    }



    private boolean isUserHaveCourseInCart(Integer userId, Long courseId) {
        return cartRepository.findByUserId(userId).get().getCourses()
                .contains(courseRepository.getReferenceById(courseId));
    }

    private boolean isUserHaveCourse(Integer userId, Long courseId) {
        return userRepository.findByIdUsers(Long.valueOf(userId)).get().getCourses()
                .contains(courseRepository.findByIdCourses(courseId).get());
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

    private boolean areCoursesInCart(HttpServletRequest request) {
       return cartRepository.findByUserId(getUserId(request)).isPresent();
    }

    private int getCoursesInCartSize(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request))
                .get()
                .getCourses().size();
    }

    private int getUserId(HttpServletRequest request) {
        return Integer.parseInt(request.getSession().getAttribute("user").toString());
    }
}
