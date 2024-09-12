package vn.edu.likelion.DemoAuthJWT.common.enums;

/**
 * ErrorCode -
 *
 * @param
 * @return
 * @throws
 */

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNAUTHORIZED("Bạn không có quyền truy cập vào đây"),
    ;
    private final String message;

    ErrorCode( String message) {
        this.message = message;
    }

}
