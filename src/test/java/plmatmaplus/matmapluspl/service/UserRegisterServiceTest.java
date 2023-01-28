package plmatmaplus.matmapluspl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

    @InjectMocks
    private UserRegisterService userRegisterService;
    @Mock
    private UserRepository userRepository;

    @Test
    void registerUser_shouldRegisterNewUser() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");
        UserEntity userEntity  = new UserEntity("user","password","email");

        //when
        userRegisterService.registerUser(userRegisterDTO);

        //then
        then(userRepository).should().save(userEntity);
    }

    @Test
    void isEmailTaken_whenEmailIsTaken_shouldReturnTrue() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");
        UserEntity userEntity  = new UserEntity("user","password","email");
        given(userRepository.findUserByEmail(userRegisterDTO.getEmail())).willReturn(Optional.of(userEntity));

        //then
        boolean isEmailTaken = userRegisterService.isEmailTaken(userRegisterDTO);
        assertTrue(isEmailTaken);
    }

    @Test
    void isEmailTaken_whenEmailIsNoTaken_shouldReturnFalse() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");
        given(userRepository.findUserByEmail(userRegisterDTO.getEmail())).willReturn(Optional.empty());

        //then
        boolean isEmailTaken = userRegisterService.isEmailTaken(userRegisterDTO);
        assertFalse(isEmailTaken);
    }

    @Test
    void isPasswordLengthProperty_whenPasswordIsLongerThan6Letters_shouldReturnTrue() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user",null,"password","email");

        //when
        userRegisterDTO.setPassword("1234567");

        //then
        boolean isPasswordLengthProperty  = userRegisterService.isPasswordLengthProperty(userRegisterDTO);
        assertTrue(isPasswordLengthProperty);
    }

    @Test
    void isPasswordLengthProperty_whenPasswordIsLongerEqualTo6Letters_shouldReturnTrue() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user",null,"password","email");

        //when
        userRegisterDTO.setPassword("123456");

        //then
        boolean isPasswordLengthProperty  = userRegisterService.isPasswordLengthProperty(userRegisterDTO);
        assertTrue(isPasswordLengthProperty);
    }

    @Test
    void isPasswordLengthProperty_whenPasswordIsNoLongerThan6Letters_shouldReturnFalse() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user",null,"password","email");

        //when
        userRegisterDTO.setPassword("12345");

        //then
        boolean isPasswordLengthProperty  = userRegisterService.isPasswordLengthProperty(userRegisterDTO);
        assertFalse(isPasswordLengthProperty);
    }

    @Test
    void isUserExist_whenUserExist_shouldReturnTrue() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");
        UserEntity userEntity  = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userRegisterDTO.getUsername())).willReturn(Optional.of(userEntity));

        //then
        boolean isUserExist = userRegisterService.isUserExist(userRegisterDTO);
        assertTrue(isUserExist);
    }

    @Test
    void isUserExist_whenUserNoExist_shouldReturnFalse() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");
        given(userRepository.findUserByUsername(userRegisterDTO.getUsername())).willReturn(Optional.empty());

        //then
        boolean isUserExist = userRegisterService.isUserExist(userRegisterDTO);
        assertFalse(isUserExist);
    }

    @Test
    void isPasswordSame_whenPasswordIsSame_shouldReturnTrue() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","password","email");

        //then
        boolean isPasswordSame = userRegisterService.isPasswordSame(userRegisterDTO);
        assertTrue(isPasswordSame);
    }

    @Test
    void isPasswordSame_whenPasswordIsNotSame_shouldReturnFalse() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password1","password2","email");

        //then
        boolean isPasswordSame = userRegisterService.isPasswordSame(userRegisterDTO);
        assertFalse(isPasswordSame);
    }

    @Test
    void isPasswordSame_whenPasswordHaveTheSameLettersButAnotherSizeOfLetters_shouldReturnFalse() {
        //given
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("user","password","PASSWORD","email");

        //then
        boolean isPasswordSame = userRegisterService.isPasswordSame(userRegisterDTO);
        assertFalse(isPasswordSame);
    }



}