package vn.edu.likelion.DemoAuthJWT.common;

import vn.edu.likelion.DemoAuthJWT.models.User;

/**
 * Role -
 *
 * @param
 * @return
 * @throws
 */
public enum Role {

    ADMIN("admin"),
    USER("user");

    private String role;

    Role(String role){
        this.role = role;
    }
    public String getValue(){
        return role;
    }
}
