package vn.edu.likelion.DemoAuthJWT.services;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.DemoAuthJWT.common.exceptions.AppException;
import vn.edu.likelion.DemoAuthJWT.common.util.EmailUtil;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.repositories.UserRepository;

import java.security.SecureRandom;
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


    private static final int PASSWORD_LENGTH = 16;
    private static final String PASSWORD_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom random;
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, EmailUtil emailUtil) {
        this.passwordEncoder = passwordEncoder;
        this.random = new SecureRandom();
        this.userRepository = userRepository;
        this.emailUtil = emailUtil;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Transactional
    public String forgotPassword(String email) throws AppException, MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            String newPassword = generateNewPassword();
            emailUtil.sendNewPasswordEmail(email,newPassword);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

        return "Please check your email to set new password to your account";
    }

    public String generateNewPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(PASSWORD_CHARS.length());
            password.append(PASSWORD_CHARS.charAt(randomIndex));
        }
        return password.toString();
    }
}
