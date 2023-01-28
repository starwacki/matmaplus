package plmatmaplus.matmapluspl.controller;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import plmatmaplus.matmapluspl.service.CartService;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    @Test
    void cart_whenSessionIsNoActive_shouldReturnUserMustLoginView() {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        given(cartService.isNoActiveSession(httpServletRequest)).willReturn(true);

        //then
        String expectedReturnedView = RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        String actualReturnedView = cartController.cart(mock(Model.class),httpServletRequest);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void cart_whenSessionIsActive_shouldReturnUserCartView() {
        //given
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        given(cartService.isNoActiveSession(httpServletRequest)).willReturn(false);

        //then
        String expectedReturnedView = Views.CART_VIEW.toString();
        String actualReturnedView = cartController.cart(mock(Model.class),httpServletRequest);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void removeFromCart_shouldReturnRedirectedView() {
        //then
        String expectedReturnedView = Views.CART_VIEW.toString();
        String actualReturnedView = cartController.removeFromCart(1,mock(Model.class),mock(HttpServletRequest.class));
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void removeFromCart_shouldRemoveFromCart() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        long index = 1;

        //when
        cartController.removeFromCart(index,mock(Model.class),request);

        //then
        verify(cartService).removeCourseFromCart(index,request);
    }

    @Test
    void cartWithPromoCode_whenPromoCodeExist_shouldReturnCartView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        String code = "code";
        given(cartService.isPromoCodeExist(code)).willReturn(true);


        //then
        String expectedReturnedView = Views.CART_VIEW.toString();
        String actualReturnedView = cartController.cartWithPromoCode(code,mock(Model.class),request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void cartWithPromoCode_whenPromoCodeNoExist_shouldReturnWrongPromoCodeCartView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        String code = "code";
        given(cartService.isPromoCodeExist(code)).willReturn(false);


        //then
        String expectedReturnedView = RedirectViews.WRONG_PROMO_CODE_CART_VIEW.toString();
        String actualReturnedView = cartController.cartWithPromoCode(code,mock(Model.class),request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void  addCourseToCart_whenSessionIsNoActive_shouldReturnMustLoginView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(cartService.isNoActiveSession(request)).willReturn(true);

        //then
        String expectedReturnView = RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        String actualReturnView  = cartController.addCourseToCart(1l,request);

        Assertions.assertEquals(expectedReturnView,actualReturnView);
    }

    @Test
    void  addCourseToCart_whenSessionIsActive_shouldReturnRedirectShopView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(cartService.isNoActiveSession(request)).willReturn(false);

        //then
        String expectedReturnView = RedirectViews.SHOP_VIEW.toString();
        String actualReturnView  = cartController.addCourseToCart(1l,request);

        Assertions.assertEquals(expectedReturnView,actualReturnView);
    }

    @Test
    void  addCourseToCart_whenSessionIsActive_shouldAddCourseToUserCart() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(cartService.isNoActiveSession(request)).willReturn(false);
        long productId = 1l;

        //then
        cartController.addCourseToCart(productId,request);
        verify(cartService).addCourseToUserCart(request,productId);
    }




}