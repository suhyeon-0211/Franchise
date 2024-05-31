package com.franchise.Franchise.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.franchise.Franchise.enums.Role;

import lombok.Data;

@Data
public class RequestConnector {
    
    @Email
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message = "직급을 입력해주세요 (ADMIN, MANAGER)")
    private String role;

    private Long storeId;
}
