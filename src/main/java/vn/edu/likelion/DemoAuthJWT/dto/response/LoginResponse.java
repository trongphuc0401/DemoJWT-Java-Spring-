package vn.edu.likelion.DemoAuthJWT.dto.response;

import lombok.*;

/**
 * LoginResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;

    private long expiresIn;


}
