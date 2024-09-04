package vn.edu.likelion.DemoAuthJWT.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.DemoAuthJWT.dto.request.LoginUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.RegisterUserRequest;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.repositories.UserRepository;

/**
 * AuthenticationService -
 *
 * @param
 * @return
 * @throws
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(RegisterUserRequest registerUserRequest) {
        User user = User.builder()
                .fullName(registerUserRequest.getFullName())
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .build();

        return userRepository.save(user);

    }

    public User authenticate(LoginUserRequest loginUserRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequest.getEmail(),
                loginUserRequest.getPassword()
        ));
        return userRepository.findByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Login faild. Please try again."));
    }
}
