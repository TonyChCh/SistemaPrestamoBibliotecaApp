package webprog2.sistemaprestamobibliotecaapp.repository;

import org.springframework.stereotype.Repository;
import webprog2.sistemaprestamobibliotecaapp.data.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simple in-memory repository implementation for initial testing.
 * This is a temporary implementation until a persistence layer is added.
 */
@Repository
public class UserRepositoryInMemoryImpl implements UserRepository {

    private final List<User> UserList = new ArrayList<>();

    @Override
    public Optional<User> findByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return Optional.empty();
        }
        String trimmedUserName = userName.trim();
        // Check if the user already exists in the list
        for (User user : UserList) {
            if (user.getUserName().equalsIgnoreCase(trimmedUserName)) {
                return Optional.of(user);
            }
        }
        // If not found, create a new user and add it to the list
        User newUser = new User(trimmedUserName, User.Type.REGULAR);
        UserList.add(newUser);
        return Optional.of(newUser);
    }
}
