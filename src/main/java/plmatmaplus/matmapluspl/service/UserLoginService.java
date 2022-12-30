package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserLoginDTO;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
public class UserLoginService  {

    private final UserRepository userRepository;

    @Autowired
    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExist(UserLoginDTO userLoginDTO) {
        return userRepository.findUserByUsername(userLoginDTO.getUsername()).isPresent();
    }

    public boolean isPasswordProperty(UserLoginDTO userLoginDTO) {
        return userRepository.findUserByUsername(userLoginDTO.getUsername()).get().getPassword().equals(userLoginDTO.getPassword());
    }

    public UserEntity getExistUser(UserLoginDTO userLoginDTO) {
        return userRepository.findUserByUsername(userLoginDTO.getUsername()).get();
    }
}
