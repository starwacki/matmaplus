package plmatmaplus.matmapluspl.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserRegisterDTO;
import plmatmaplus.matmapluspl.entity.Role;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegisterService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        encodePassword(userRegisterDTO);
        save(userRegisterDTO);
    }

    public void save(UserRegisterDTO userRegisterDTO) {
        UserEntity userEntity = mapToUser(userRegisterDTO);
        userEntity.addRolesToUser(new Role("USER"));
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

    public void encodePassword(UserRegisterDTO userRegisterDTO) {
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
    }

    private UserEntity mapToUser(UserRegisterDTO userRegisterDTO) {
        return new UserEntity(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                userRegisterDTO.getEmail());
    }
}
