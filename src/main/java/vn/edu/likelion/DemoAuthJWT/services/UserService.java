package vn.edu.likelion.DemoAuthJWT.services;

import org.springframework.stereotype.Service;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * UserService -
 *
 * @param
 * @return
 * @throws
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
