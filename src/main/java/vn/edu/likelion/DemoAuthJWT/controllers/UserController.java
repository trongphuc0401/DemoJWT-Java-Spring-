package vn.edu.likelion.DemoAuthJWT.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.DemoAuthJWT.common.restfulAPI.ResponseUtil;
import vn.edu.likelion.DemoAuthJWT.common.restfulAPI.RestAPIResponse;
import vn.edu.likelion.DemoAuthJWT.common.restfulAPI.RestAPIStatus;
import vn.edu.likelion.DemoAuthJWT.dto.request.ChangePasswordRequest;
import vn.edu.likelion.DemoAuthJWT.models.User;
import vn.edu.likelion.DemoAuthJWT.services.AuthenticationService;
import vn.edu.likelion.DemoAuthJWT.services.UserService;

import java.util.List;

/**
 * UserController -
 *
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping("/users")
public class UserController {


    private final ResponseUtil  responseUtil;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(ResponseUtil responseUtil, UserService userService,
                          AuthenticationService authenticationService) {
        this.responseUtil = responseUtil;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currUser);
    }

    @GetMapping("/admin/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<RestAPIResponse<Object>> changePassword(@PathVariable(value = "userId") Integer userId,
                                                                  @RequestBody ChangePasswordRequest changePasswordRequest)
    {
        return responseUtil.buildResponse(RestAPIStatus.OK,authenticationService.changePassword(userId , changePasswordRequest.getCurrentPassword(),
                changePasswordRequest.getNewPassword()), HttpStatus.ACCEPTED);
    }

    @PutMapping("/password")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<RestAPIResponse<Object>> forgotPassword(@RequestParam String email) {
        return responseUtil.buildResponse(RestAPIStatus.OK,userService.forgotPassword(email),HttpStatus.OK);

    }

}
