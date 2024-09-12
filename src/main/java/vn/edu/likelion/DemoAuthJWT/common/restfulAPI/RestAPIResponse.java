package vn.edu.likelion.DemoAuthJWT.common.restfulAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.edu.likelion.DemoAuthJWT.common.enums.ErrorCode;

import java.time.LocalDateTime;

/**
 * RestAPIResponse -
 *
 * @param
 * @return
 * @throws
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestAPIResponse<T extends Object> {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;
    private LocalDateTime timestamp;

    public RestAPIResponse(RestAPIStatus restAPIStatus , T data) {
        if (restAPIStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }
        this.status = restAPIStatus.getCode();
        this.message = restAPIStatus.getDescription();
        this.data = data;
        this.timestamp = LocalDateTime.now();


    }

    public RestAPIResponse(RestAPIStatus restAPIStatus, ErrorCode errorCode) {

        if (restAPIStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = restAPIStatus.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();

    }
}
