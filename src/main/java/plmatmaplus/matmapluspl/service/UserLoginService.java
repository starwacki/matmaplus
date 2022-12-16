package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.repository.UserRepository;

@Service
public class UserLoginService {

    private final UserRepository userRepository;

    @Autowired
    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
