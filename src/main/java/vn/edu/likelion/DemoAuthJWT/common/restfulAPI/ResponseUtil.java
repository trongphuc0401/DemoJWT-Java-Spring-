package vn.edu.likelion.DemoAuthJWT.common.restfulAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.edu.likelion.DemoAuthJWT.common.enums.ErrorCode;

/**
 * ResponseUtli -
 *
 * @param
 * @return
 * @throws
 */

@Component
public class ResponseUtil {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public ResponseUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private RestAPIResponse<Object> createResponse(RestAPIStatus restAPIStatus , ErrorCode errorCode) {
        return new RestAPIResponse<>(restAPIStatus,errorCode);
    }

    private  RestAPIResponse<Object> createResponse(RestAPIStatus restAPIStatus , Object data) {
        return new RestAPIResponse<>(restAPIStatus, data);
    }

    public ResponseEntity< RestAPIResponse<Object>> buildResponse(RestAPIStatus restAPIStatus , ErrorCode errorCode, HttpStatus httpStatus) {

        return new ResponseEntity<>(createResponse(restAPIStatus, errorCode), httpStatus);
    }

    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restAPIStatus , Object data, HttpStatus httpStatus) {

        return new ResponseEntity<>(createResponse(restAPIStatus,data ), httpStatus);
    }





}
