package plmatmaplus.matmapluspl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.entity.Role;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
public class UserRegisterService {
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserRegisterDTO userRegisterDTO, Role role) {
        UserEntity userEntity = mapToUser(userRegisterDTO);
        userEntity.addRolesToUser(role);
        userRepository.save(userEntity);
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

    private UserEntity mapToUser(UserRegisterDTO userRegisterDTO) {
        return new UserEntity(userRegisterDTO.getUsername(),userRegisterDTO.getPassword(),userRegisterDTO.getEmail());
    }
}
