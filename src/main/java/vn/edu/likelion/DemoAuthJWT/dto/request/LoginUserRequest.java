package vn.edu.likelion.DemoAuthJWT.dto.request;

import lombok.Data;

/**
 * LoginUserRequest -
 *
 * @param
 * @return
 * @throws
 */
@Data
public class LoginUserRequest {
    private String email;

    private String password;

}
