package webprog2.sistemaprestamobibliotecaapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.repository.UserRepository;
import java.util.Optional;

/**
 * Service responsible for user-related business logic.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate a user by username. For initial purposes this only looks
     * up the user by username and returns it if present.
     *
     * @param username the username to authenticate
     * @return Optional containing the User if found
     */
    public Optional<User> authenticate(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findByUserName(username.trim());
    }
}
