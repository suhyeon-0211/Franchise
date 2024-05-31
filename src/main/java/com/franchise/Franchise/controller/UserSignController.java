package com.franchise.Franchise.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.request.RequestConnector;
import com.franchise.Franchise.request.SigninRequest;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.user.ResponseConnector;
import com.franchise.Franchise.service.sign.SignService;
import com.franchise.Franchise.token.JwtToken;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "SIGN", description = "유저 관리")
@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class UserSignController {
    private final SignService signService;

    @PostMapping("/sign-up")
    @Tag(name = "SIGN")
    @Operation(summary = "회원가입", description = "회원가입")
    public ResponseEntity<GenericResponse<ResponseConnector>> signup(@Valid RequestConnector request) {
        //TODO: process POST request
        
        return GenericResponse.ok(signService.signup(request));
    }

    @PostMapping("/sign-in")
    @Tag(name = "SIGN")
    @Operation(summary = "로그인", description = "로그인")
    public ResponseEntity<GenericResponse<JwtToken>> signin(@Valid SigninRequest request) {
        return GenericResponse.ok(signService.signin(request));
    }
    
    @GetMapping("/logout")
    @Tag(name = "SIGN")
    @Operation(summary = "로그아웃", description = "로그아웃")
    public ResponseEntity<GenericResponse<String>> logout(final @AuthenticationPrincipal UserAccessToken UserAccessToken) {
        signService.logout(UserAccessToken);
        return GenericResponse.ok("success");
    }
    
    
}
