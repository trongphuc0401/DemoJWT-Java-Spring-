package vn.edu.likelion.DemoAuthJWT.dto.response;

import lombok.*;

/**
 * TokenRefreshResponse -
 *
 * @param
 * @return
 * @throws
 */


@Getter
@Setter
@Builder

@NoArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;

    public TokenRefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
