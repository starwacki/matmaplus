package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.CourseCartDTO;
import plmatmaplus.matmapluspl.dto.CourseIMGLinks;
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

    private final CartRepository cartRepository;

    private final CourseRepository courseRepository;

    private final PromoCodeRepository promoCodeRepository;

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

    public void addCourseToUserCart(HttpServletRequest request, Long courseId) {
        if (!isCartExist(getUserId(request)))
            createCartForUser(getUserId(request));
        if (!isCourseExist(getUserId(request),courseId))
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

    public void removeCourse(long index,HttpServletRequest request) {
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
        double total = 0;
        for (CourseCartDTO courseCartDTO : courseCartDTOList) {
            total += courseCartDTO.getPrice();
        }
        return total;
    }

    private ArrayList<CourseCartDTO> getEmptyList() {
        return new ArrayList<>();
    }

    private List<Course> getCourses(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request)).get().getCourses();
    }
    private List<CourseCartDTO> mapToCourseCartDTOList(List<Course> courses) {
      return courses.stream().map(course -> mapToCourseCartDT(course)).toList();
    }

    private CourseCartDTO mapToCourseCartDT(Course course) {
        return new CourseCartDTO(course.getIdCourses(),
                course.getName(),
                course.getPrice(),
                course.getAdvancement(),
                getImgLink(course.getIdCourses()));
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

    private boolean areCoursesInCart(HttpServletRequest request) {
       return cartRepository.findByUserId(getUserId(request)).isPresent();
    }

    private int getCoursesInCartSize(HttpServletRequest request) {
        return cartRepository.findByUserId(getUserId(request))
                .get()
                .getCourses().size();
    }

    private String getImgLink(long courseId) {
        String link = "";
        switch ((int) courseId) {
            case 1 -> link = CourseIMGLinks.ANALIZA_MATEMATYCZNA_PODSTAWA.toString();
            case 2 -> link = CourseIMGLinks.ANALIZA_MATEMATYCZNA_ROZSZERZENIE.toString();
            case 3 -> link = CourseIMGLinks.KURS_MATURA_PODSTAWOWA.toString();
            case 4 -> link = CourseIMGLinks.KURS_MATURA_ROZSZERZONA.toString();
            case 5 -> link = CourseIMGLinks.EGZAMIN_OŚMOKLASISTY.toString();
            case 6 -> link = CourseIMGLinks.CAŁKI_NA_STUDIACH.toString();
        }
        return link;
    }

    private int getUserId(HttpServletRequest request) {
        return Integer.parseInt(request.getSession().getAttribute("user").toString());
    }
}
