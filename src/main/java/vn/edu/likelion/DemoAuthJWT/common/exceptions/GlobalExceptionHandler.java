package vn.edu.likelion.DemoAuthJWT.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.likelion.DemoAuthJWT.common.enums.ErrorCode;
import vn.edu.likelion.DemoAuthJWT.common.restfulAPI.RestAPIResponse;

/**
 * GlobalExceptionHandler -
 *
 * @param
 * @return
 * @throws
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        RestAPIResponse restAPIResponse = new RestAPIResponse();
        restAPIResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(restAPIResponse);
    }
    
}
