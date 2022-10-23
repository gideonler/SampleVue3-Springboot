package oop.io.demo.auth.payload.request;

import javax.validation.constraints.NotBlank;

public class VerificationRequest {
    @NotBlank
    String token;
    @NotBlank
    String password;
    @NotBlank
    String retypePassword;
    @NotBlank
    String contactNo;

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    
}
