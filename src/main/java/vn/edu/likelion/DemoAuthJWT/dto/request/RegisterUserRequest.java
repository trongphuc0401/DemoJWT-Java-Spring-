package vn.edu.likelion.DemoAuthJWT.dto.request;

import lombok.Data;
import vn.edu.likelion.DemoAuthJWT.common.enums.Role;

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

    private Role role;


}
