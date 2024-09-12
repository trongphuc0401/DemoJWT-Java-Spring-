package vn.edu.likelion.DemoAuthJWT.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.DemoAuthJWT.dto.request.ChangePasswordRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.LoginUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.RegisterUserRequest;
import vn.edu.likelion.DemoAuthJWT.models.RefreshToken;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.repositories.RefreshTokenRepository;
import vn.edu.likelion.DemoAuthJWT.repositories.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

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

    private final Set<String> blacklist = new ConcurrentSkipListSet<>();


    private final RefreshTokenRepository refreshTokenRepository;


    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public User signUp(RegisterUserRequest registerUserRequest) {
        User user = User.builder()
                .fullName(registerUserRequest.getFullName())
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(registerUserRequest.getRole())
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

    /*Change Password*/

    public User changePassword(Integer id,String currentPassword, String newPassword ) {

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Email not found. Please try again."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Wrong password. Please try again.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /*Log out*/
    public void addTokenToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }

    /*Refresh Token*/

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }



    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+ "Refresh token was expired. Please make a new login request");
        }

        return token;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredTokens() {
        Instant now = Instant.now();
        List<RefreshToken> expiredTokens = refreshTokenRepository.findAllExpiredTokens(now);
        if (!expiredTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(expiredTokens);
        }
    }


}
