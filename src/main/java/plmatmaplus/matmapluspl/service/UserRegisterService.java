package plmatmaplus.matmapluspl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.entity.User;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
public class UserRegisterService {
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserRegisterDTO userRegisterDTO) {
        userRepository.save(mapToUser(userRegisterDTO));
    }

    public boolean isEmailTaken(UserRegisterDTO userRegisterDTO) {
        return userRepository.findUserByEmail(userRegisterDTO.getEmail()).isPresent();
    }

    public boolean isPasswordLengthProperty(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getPassword().length() >=6;
    }

    public boolean isUserExist(UserRegisterDTO userRegisterDTO) {
        return  userRepository.findUserByUsername(userRegisterDTO.getUsername()).isPresent();
    }

    public boolean isPasswordSame(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getRepeatedPassword());
    }

    private User mapToUser(UserRegisterDTO userRegisterDTO) {
        return new User(userRegisterDTO.getUsername(),userRegisterDTO.getPassword(),userRegisterDTO.getEmail());
    }
}
