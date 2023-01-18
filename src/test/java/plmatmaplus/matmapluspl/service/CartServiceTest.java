package plmatmaplus.matmapluspl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plmatmaplus.matmapluspl.dto.CourseCartDTO;
import plmatmaplus.matmapluspl.dto.OrderDTO;
import plmatmaplus.matmapluspl.entity.*;
import plmatmaplus.matmapluspl.repository.CartRepository;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.PromoCodeRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CartServiceTest {

    private  CartService cartService;
    private  CartRepository cartRepository;
    private  CourseRepository courseRepository;
    private  PromoCodeRepository promoCodeRepository;
    private  UserRepository userRepository;


    @BeforeEach
    public  void initialize() {
        cartRepository = mock(CartRepository.class);
        courseRepository = mock(CourseRepository.class);
        promoCodeRepository = mock(PromoCodeRepository.class);
        userRepository = mock(UserRepository.class);
        cartService = new CartService(cartRepository,courseRepository,promoCodeRepository,userRepository);
    }



    @Test
    void isNoActiveSession_whenSessionIsActive_ShouldReturnFalse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);


        //when
        boolean isNoActiveSession = cartService.isNoActiveSession(request);

        //then
        Assertions.assertFalse(isNoActiveSession);
    }

    @Test
    void isNoActiveSession_whenSessionIsNoActive_ShouldReturnTrue() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        given(request.getSession()).willReturn(session);

        //when
        boolean isNoActiveSession = cartService.isNoActiveSession(request);
        given(request.getSession().getAttribute(attributeUser)).willReturn(null);


        //then
        Assertions.assertTrue(isNoActiveSession);
    }

    @Test
    void getCartSize_whenIsNoActiveSession_shouldReturnZero() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        given(request.getSession()).willReturn(session);
        String attributeUser = "user";

        //when
        given(request.getSession().getAttribute(attributeUser)).willReturn(null);
        int expectedCartSize = 0;
        int actualCartSize = cartService.getCartSize(request);

        //then
        Assertions.assertEquals(expectedCartSize,actualCartSize);
    }

    @Test
    void cartSize_whenIsActiveSessionAndNoCoursesInCart_shouldReturnZero() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        given(request.getSession()).willReturn(session);
        String attributeUser = "user";
        int userId = 1;
        session.setAttribute(attributeUser,userId);

        //when
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(1)).willReturn(Optional.empty());
        int expectedCartSize = 0;
        int actualCartSize = cartService.getCartSize(request);

        //then
        Assertions.assertEquals(expectedCartSize,actualCartSize);
    }

    @Test
    void getCartSize_whenIsActiveSessionAndFourCoursesInCart_shouldReturnFour() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Cart cart = mock(Cart.class);
        given(request.getSession()).willReturn(session);
        String attributeUser = "user";
        int userId = 1;
        session.setAttribute(attributeUser,userId);

        //when
        given(cart.getCourses()).willReturn(Set.of(new Course(),new Course(),new Course(),new Course()));
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(1)).willReturn(Optional.of(cart));
        int expectedCartSize = 4;
        int actualCartSize = cartService.getCartSize(request);

        //then
        Assertions.assertEquals(expectedCartSize,actualCartSize);
    }

    @Test
    void getCartSize_whenIsActiveSessionAndZeroCoursesInCart_shouldReturnZero() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Cart cart = mock(Cart.class);
        given(request.getSession()).willReturn(session);
        String attributeUser = "user";
        int userId = 1;
        session.setAttribute(attributeUser,userId);

        //when
        given(cart.getCourses()).willReturn(Set.of());
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(1)).willReturn(Optional.of(cart));
        int expectedCartSize = 0;
        int actualCartSize = cartService.getCartSize(request);

        //then
        Assertions.assertEquals(expectedCartSize,actualCartSize);
    }

    @Test
    void addCourseToUserCart_whenCartNotExist_shouldCreateCart() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int userId = 1;
        long courseId = 1;
        UserEntity userEntity = new UserEntity("user","password","email");
        userEntity.setIdUsers((long) userId);
        Cart cart = new Cart(userId);
        Course course = new Course();
        session.setAttribute(attributeUser,userId);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.save(cart)).willReturn(cart);


        //when
        Assertions.assertEquals(cartRepository.findByUserId(userId),Optional.empty());

        //and
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(cart));
        given(userRepository.findByIdUsers(1l)).willReturn(Optional.of(userEntity));
        given(courseRepository.findByIdCourses(courseId)).willReturn(Optional.of(course));

        //then
        cartService.addCourseToUserCart(request,courseId);
        Assertions.assertEquals(cartRepository.findByUserId(1),Optional.of(cart));
    }


    @Test
    void getCoursesCartDTOList_whenSessionNoExistAndCartNoExist_shouldReturnEmptyList() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";

        //when
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(null);

        //then
        List<CourseCartDTO> actualList = cartService.getCourseCartDTOList(request);
        List<CourseCartDTO> expectedList = new ArrayList<>();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void getCoursesCartDTOList_whenSessionExistAndCartNoExist_shouldReturnEmptyList() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int userId = 1;

        //when
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(userId)).willReturn(Optional.empty());

        //then
        List<CourseCartDTO> actualList = cartService.getCourseCartDTOList(request);
        List<CourseCartDTO> expectedList = new ArrayList<>();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void getCoursesCartDTOList_whenSessionExistAndCartExistAndCartNoHaveCourses_shouldReturnEmptyList() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int userId = 1;
        Cart cart = new Cart(userId);

        //when
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(cart));

        //then
        List<CourseCartDTO> actualList = cartService.getCourseCartDTOList(request);
        List<CourseCartDTO> expectedList = new ArrayList<>();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void getCoursesCartDTOList_whenSessionExistAndCartExistAndCartHaveTwoCourses_shouldReturnListWithTwoCourses() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int userId = 1;
        Cart cart = new Cart(userId);
        Course course1 = new Course(1L,"test1",100.0,"test",new HashSet<>(),new HashSet<>(),new CourseDetails(),new HashSet<>());
        Course course2 = new Course(2L,"test2",100.0,"test",new HashSet<>(),new HashSet<>(),new CourseDetails(),new HashSet<>());
        cart.getCourses().add(course1);
        cart.getCourses().add(course2);

        //when
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(userId);
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(cart));

        //then
        List<CourseCartDTO> actualList = cartService.getCourseCartDTOList(request);
        int actualListSize = actualList.size();
        int expectedListSize = 2;
        Assertions.assertEquals(expectedListSize,actualListSize);
    }

    @Test
    void getOrderWithWrongPromoCode_whenPromoCodeIsWrong_shouldReturnOrderWithNotChangedTotal() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //then
        OrderDTO order = cartService.getOrderWithWrongPromoCode(coursesInCart);
        double expectedTotal = 200;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithWrongPromoCode_whenPromoCodeIsWrongAndNoCoursesInCart_shouldReturnOrderWithZeroTotal() {
        //given
        List<CourseCartDTO> coursesInCart = List.of();

        //then
        OrderDTO order = cartService.getOrderWithWrongPromoCode(coursesInCart);
        double expectedTotal = 0;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithWPromoCode_whenPromoCodeIsGoodAndNoCoursesInCart_shouldReturnOrderWithZeroTotal() {
        //given
        List<CourseCartDTO> coursesInCart = List.of();
        int codePercentValue = 10;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedTotal = 0;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs10PercentAndWhenCoursesCartTotalIs200_shouldReturn180CartTotal() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 10;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedTotal = 180;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs10PercentAndWhenCoursesCartTotalIs200_shouldReturn20PromoValue() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 10;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedPromoValue = 20;
        double actualPromoValue = order.getPromoValue();
        Assertions.assertEquals(expectedPromoValue,actualPromoValue);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs5PercentAndWhenCoursesCartTotalIs200_shouldReturn190CartTotal() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 5;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedTotal = 190;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs5PercentAndWhenCoursesCartTotalIs200_shouldReturn10PromoValue() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 5;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedPromoValue = 10;
        double actualPromoValue = order.getPromoValue();
        Assertions.assertEquals(expectedPromoValue,actualPromoValue);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs100PercentAndWhenCoursesCartTotalIs200_shouldReturn0CartTotal() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 100;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedTotal = 0;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }


    @Test
    void getOrderWithPromoCode_whenPromoCodeIs100PercentAndWhenCoursesCartTotalIs200_shouldReturn200PromoValue() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 100;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedPromoValue = 200;
        double actualPromoValue = order.getPromoValue();
        Assertions.assertEquals(expectedPromoValue,actualPromoValue);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs0PercentAndWhenCoursesCartTotalIs200_shouldReturn200CartTotal() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 0;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedTotal = 200;
        double actualTotal = order.getEndCartTotal();
        Assertions.assertEquals(expectedTotal,actualTotal);
    }

    @Test
    void getOrderWithPromoCode_whenPromoCodeIs0PercentAndWhenCoursesCartTotalIs200_shouldReturn0PromoValue() {
        //given
        CourseCartDTO courseCartDTO1 = new CourseCartDTO(1L,"test1",100.0,"testAdvancement1","link1");
        CourseCartDTO courseCartDTO2 = new CourseCartDTO(2L,"test1",100.0,"testAdvancement1","link1");
        List<CourseCartDTO> coursesInCart = List.of(courseCartDTO1,courseCartDTO2);

        //when
        int codePercentValue = 0;

        //then
        OrderDTO order = cartService.getOrderWithPromoCode(coursesInCart,codePercentValue);
        double expectedPromoValue = 0;
        double actualPromoValue = order.getPromoValue();
        Assertions.assertEquals(expectedPromoValue,actualPromoValue);
    }

    @Test
    void isPromoCodeExist_whenPromoCodeExist_shouldReturnTrue() {
        //given
        String promoCodeName = "promoCode";
        PromoCode  promoCode = new PromoCode(promoCodeName,20);
        given(promoCodeRepository.findPromoCodeByCode(promoCodeName)).willReturn(Optional.of(promoCode));

        //then
        boolean isPromoCodeExist  = cartService.isPromoCodeExist(promoCodeName);
        Assertions.assertTrue(isPromoCodeExist);
    }

    @Test
    void isPromoCodeExist_whenPromoCodeNoExist_shouldReturnFalse() {
        //given
        String promoCodeName = "promoCode";
        given(promoCodeRepository.findPromoCodeByCode(promoCodeName)).willReturn(Optional.empty());

        //then
        boolean isPromoCodeExist  = cartService.isPromoCodeExist(promoCodeName);
        Assertions.assertFalse(isPromoCodeExist);
    }

    @Test
    void getPromoCodePercentValue_whenPromoCodePercentValueIs10_shouldReturn10() {
        //given
        String promoCodeName = "promoCode";
        PromoCode  promoCode = new PromoCode(promoCodeName,10);
        given(promoCodeRepository.findPromoCodeByCode(promoCodeName)).willReturn(Optional.of(promoCode));

        //when
        int expectedPromoCodeValue = 10;

        //then
        int actualPromoCodeValue = cartService.getPromoCodePercentValue(promoCodeName);
        Assertions.assertEquals(expectedPromoCodeValue,actualPromoCodeValue);
    }

    @Test
    void removeCourseFromCart_whenCartHaveOneCourse_shouldReturnEmptySet() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        long courseId = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        Cart cart = new Cart(idUser);
        Course course = mock(Course.class);
        course.setIdCourses(courseId);
        cart.getCourses().add(course);
        given(cartRepository.findByUserId(idUser)).willReturn(Optional.of(cart));
        given(courseRepository.findByIdCourses(courseId)).willReturn(Optional.of(course));
        cartService.removeCourseFromCart(courseId,request);

        //then
         Set<Course> courses = cart.getCourses();
         int actualCoursesSize = courses.size();
         int expectedSize  = 0;
         Assertions.assertEquals(expectedSize,actualCoursesSize);
    }

    @Test
    void removeCourseFromCart_whenCartHaveTwoCourse_shouldReturnSetWithOneCourse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        long courseOneId = 1;
        long courseSecondId = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        Cart cart = new Cart(idUser);
        Course course1 = mock(Course.class);
        Course course2 = mock(Course.class);
        course1.setIdCourses(courseOneId);
        course2.setIdCourses(courseSecondId);
        cart.getCourses().add(course1);
        cart.getCourses().add(course2);
        given(cartRepository.findByUserId(idUser)).willReturn(Optional.of(cart));
        given(courseRepository.findByIdCourses(courseOneId)).willReturn(Optional.of(course1));
        cartService.removeCourseFromCart(courseOneId,request);

        //then
        Set<Course> courses = cart.getCourses();
        int actualCoursesSize = courses.size();
        int expectedSize  = 1;
        Assertions.assertEquals(expectedSize,actualCoursesSize);
    }











}