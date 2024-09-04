package vn.edu.likelion.DemoAuthJWT.dto.request;

import lombok.Data;

/**
 * RegisterUserRequest -
 *
 * @param
 * @return
 * @throws
 */
@Data
public class RegisterUserRequest {
    private String email;

    private String password;

    private String fullName;
}
