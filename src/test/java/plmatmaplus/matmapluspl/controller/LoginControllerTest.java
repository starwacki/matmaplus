package plmatmaplus.matmapluspl.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.service.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;
    @Mock
    private UserLoginService userLoginService;

    @Test
    void login_whenUserNoExist_shouldReturnWrongLoginView() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("username","password");
        given(userLoginService.isUserExist(userLoginDTO)).willReturn(false);

        //then
        String expectedReturnView = RedirectViews.WRONG_LOGIN_VIEW.toString();
        String actualReturnView = loginController.login(userLoginDTO,mock(HttpServletRequest.class));
        Assertions.assertEquals(expectedReturnView,actualReturnView);
    }

    @Test
    void login_whenUserLoginAndPasswordNotProperty_shouldReturnWrongLoginView() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("username","password");
        given(userLoginService.isUserExist(userLoginDTO)).willReturn(true);
        given(userLoginService.isUserLoginAndPasswordProperty(userLoginDTO)).willReturn(false);

        //then
        String expectedReturnView = RedirectViews.WRONG_LOGIN_VIEW.toString();
        String actualReturnView = loginController.login(userLoginDTO,mock(HttpServletRequest.class));
        Assertions.assertEquals(expectedReturnView,actualReturnView);
    }

    @Test
    void login_whenUserExistAndLoginAndPasswordProperty_shouldReturnSuccesLoginView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserLoginDTO userLoginDTO = new UserLoginDTO("username","password");
        given(userLoginService.isUserExist(userLoginDTO)).willReturn(true);
        given(userLoginService.isUserLoginAndPasswordProperty(userLoginDTO)).willReturn(true);
        given(request.getSession()).willReturn(mock(HttpSession.class));

        //then
        String expectedReturnView = RedirectViews.SUCCESS_LOGIN_VIEW.toString();
        String actualReturnView = loginController.login(userLoginDTO,request);
        Assertions.assertEquals(expectedReturnView,actualReturnView);
    }




}