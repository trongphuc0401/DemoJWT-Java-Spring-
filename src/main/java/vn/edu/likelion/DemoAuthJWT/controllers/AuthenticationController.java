package vn.edu.likelion.DemoAuthJWT.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.DemoAuthJWT.dto.request.LoginUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.RegisterUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.TokenRefreshRequest;
import vn.edu.likelion.DemoAuthJWT.dto.response.LoginResponse;
import vn.edu.likelion.DemoAuthJWT.dto.response.TokenRefreshResponse;
import vn.edu.likelion.DemoAuthJWT.models.RefreshToken;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.services.AuthenticationService;
import vn.edu.likelion.DemoAuthJWT.services.JwtService;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthenticationController -
 *
 * @param
 * @return
 * @throws
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService,
                                    @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {

        User registeredUser = authenticationService.signUp(registerUserRequest);
        return ResponseEntity.ok(registeredUser);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        User authenticaUser = authenticationService.authenticate(loginUserRequest);

        String jwtToken = jwtService.generateToken(authenticaUser);

        RefreshToken refreshToken = authenticationService.createRefreshToken(authenticaUser.getId());
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken()) // Include the refresh token in the response
                .expiresIn(jwtService.getExpirationTime()) // Access token expiration time
                .build();

        return ResponseEntity.ok(loginResponse);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authorizationHeader  = request.getHeader("Authorization");

        if (authorizationHeader  != null && authorizationHeader .startsWith("Bearer ")) {
            String token = authorizationHeader .substring(7);
            authenticationService.addTokenToBlacklist(token);
            return ResponseEntity.ok("Logged out successfully");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid logout request");
        }
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {

        String refreshToken = tokenRefreshRequest.getToken();
        return authenticationService.findByToken(refreshToken)
                .map(authenticationService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtService.generateTokenFromUsername(user.getUsername());
                    TokenRefreshResponse loginResponse = TokenRefreshResponse.builder()
                            .accessToken(newAccessToken)
                            .build();
                    return ResponseEntity.ok(loginResponse);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }


}
