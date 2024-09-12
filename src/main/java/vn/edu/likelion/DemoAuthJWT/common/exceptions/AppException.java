package vn.edu.likelion.DemoAuthJWT.common.exceptions;

import lombok.Getter;
import lombok.Setter;
import vn.edu.likelion.DemoAuthJWT.common.enums.ErrorCode;

/**
 * AppException -
 *
 * @param
 * @return
 * @throws
 */

@Getter
@Setter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
