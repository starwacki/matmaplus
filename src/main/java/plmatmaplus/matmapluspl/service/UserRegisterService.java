package plmatmaplus.matmapluspl.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        save(userRegisterDTO);
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

    private void save(UserRegisterDTO userRegisterDTO) {
        UserEntity userEntity = mapToUser(userRegisterDTO);
        userRepository.save(userEntity);
    }

    private UserEntity mapToUser(UserRegisterDTO userRegisterDTO) {
        return new UserEntity(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                userRegisterDTO.getEmail());
    }
}
