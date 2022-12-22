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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    private CourseRepository courseRepository;

    private PromoCodeRepository promoCodeRepository;

    @Autowired
    public CartService(CartRepository cartRepository,CourseRepository courseRepository,PromoCodeRepository promoCodeRepository) {
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.promoCodeRepository = promoCodeRepository;
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
        if (!isCartExist(userId))
            createCartForUser(userId);
        if (!isCourseExist(userId,courseId))
            addCourse(userId,courseId);
    }

    public List<CourseCartDTO> getCourseCartDTOList(HttpServletRequest request) {
        if (isNoActiveSession(request) || !isCartExist(request))
            return getEmptyList();
        else
            return mapToCourseCartDTOList(getCourses(request));
    }

    public OrderDTO getOrder(List<CourseCartDTO> courseCartDTOList) {
        return new OrderDTO(getCartTotal(courseCartDTOList),
                false,
                0,
                getCartTotal(courseCartDTOList));
    }

    public OrderDTO getOrder(List<CourseCartDTO> courseCartDTOList,int code) {
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

    private int calculatePromoValue(double cartTotal,int code) {
        return (int) (cartTotal/100*code);
    }

    private double calculateEndCartTotal(double cartTotal, int promoValue) {
        return cartTotal-promoValue;
    }


    private boolean isCartExist(HttpServletRequest request) {
        return cartRepository.findByUserId(Integer.parseInt(
                request.getSession().getAttribute("user").toString()
        )).isPresent();
    }

    private double getCartTotal(List<CourseCartDTO> courseCartDTOList) {
        double total = 0;
        for (int i = 0; i < courseCartDTOList.size() ; i++) {
            total+=courseCartDTOList.get(i).getPrice();
        }
        return total;
    }


    private ArrayList<CourseCartDTO> getEmptyList() {
        return new ArrayList<>();
    }

    private List<Course> getCourses(HttpServletRequest request) {
        return cartRepository.findByUserId(Integer.parseInt
                (request.getSession().getAttribute("user").toString())).get().getCourses().stream().toList();
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