package plmatmaplus.matmapluspl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserLoginServiceTest {

    private  UserRepository userRepository;
    private  UserLoginService userLoginService;

    @BeforeEach
    public  void initialize() {
         userRepository = mock(UserRepository.class);
         userLoginService = new UserLoginService(userRepository);
    }


    @Test
    void existUser_shouldReturnUserExistTrue() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user","password");
        UserEntity userEntity = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.of(userEntity));

        //when
        boolean isUerExist = userLoginService.isUserExist(userLoginDTO);

        //then
        Assertions.assertTrue(isUerExist);
    }

    @Test
    void noExistUser_shouldReturnUserExistFalse() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user","password");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.empty());

        //when
        boolean isUerExist = userLoginService.isUserExist(userLoginDTO);

        //then
        Assertions.assertFalse(isUerExist);
    }

    @Test
    void propertyUsernameAndPropertyPassword_shouldReturnTrue() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user","password");
        UserEntity userEntity = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.of(userEntity));

        //when
        boolean isUsernameAndPasswordAreProperty = userLoginService.isUserLoginAndPasswordProperty(userLoginDTO);

        //then
        Assertions.assertTrue(isUsernameAndPasswordAreProperty);
    }

    @Test
    void noPropertyUsernameAndPropertyPassword_shouldReturnFalse() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user1","password");
        UserEntity userEntity = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.of(userEntity));

        //when
        boolean isUsernameAndPasswordProperty = userLoginService.isUserLoginAndPasswordProperty(userLoginDTO);

        //then
        Assertions.assertFalse(isUsernameAndPasswordProperty);
    }

    @Test
    void propertyUsernameAndNoPropertyPassword_shouldReturnFalse() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user","password1");
        UserEntity userEntity = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.of(userEntity));

        //when
        boolean isUsernameAndPasswordProperty = userLoginService.isUserLoginAndPasswordProperty(userLoginDTO);

        //then
        Assertions.assertFalse(isUsernameAndPasswordProperty);
    }

    @Test
    void noPropertyUsernameAndNoPropertyPassword_shouldReturnFalse() {
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO("user1","password1");
        UserEntity userEntity = new UserEntity("user","password","email");
        given(userRepository.findUserByUsername(userLoginDTO.getUsername())).willReturn(Optional.of(userEntity));

        //when
        boolean isUsernameAndPasswordProperty = userLoginService.isUserLoginAndPasswordProperty(userLoginDTO);

        //then
        Assertions.assertFalse(isUsernameAndPasswordProperty);
    }



}