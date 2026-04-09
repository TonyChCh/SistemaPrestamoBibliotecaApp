package webprog2.sistemaprestamobibliotecaapp.service;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import webprog2.sistemaprestamobibliotecaapp.repository.UserRepository;

import java.time.LocalDateTime;
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

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        if (id == null || id < 0) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserFields(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (updatedUser.getType() != null) {
                        existingUser.setType(updatedUser.getType());
                    }
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Metodo para inicializar usuarios de prueba
     @PostConstruct
     void init() {
        User regularUser = userRepository.findByUserName("john_doe")
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User adminUser = userRepository.findByUserName("chuck_norris")
                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

        regularUser.setPassword(passwordEncoder.encode("test"));
        userRepository.save(regularUser);

        adminUser.setPassword(passwordEncoder.encode("test"));
        userRepository.save(adminUser);
    }
}
