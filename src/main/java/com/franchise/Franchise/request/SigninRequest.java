package com.franchise.Franchise.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class SigninRequest {
    
    @Email
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    public SigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
