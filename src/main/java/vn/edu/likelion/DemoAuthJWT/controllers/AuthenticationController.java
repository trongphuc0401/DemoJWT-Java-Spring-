package vn.edu.likelion.DemoAuthJWT.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.DemoAuthJWT.dto.request.LoginUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.request.RegisterUserRequest;
import vn.edu.likelion.DemoAuthJWT.dto.response.LoginResponse;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.services.AuthenticationService;
import vn.edu.likelion.DemoAuthJWT.services.JwtService;

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

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
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
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);

    }


}
