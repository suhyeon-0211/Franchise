package com.franchise.Franchise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.entity.TokenConnector;
import com.franchise.Franchise.response.GenericResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "MAIN", description = "유저 정보 및 권한 확인")
@RestController
@RequestMapping("/main")
public class MainController {
    
    @GetMapping("/get-user-info")
    @Tag(name = "MAIN")
    @Operation(summary = "유저 정보 조회", description = "토큰으로 들어온 유저 정보를 조회합니다.")
    public ResponseEntity<GenericResponse<TokenConnector>> getUserInfo(final @AuthenticationPrincipal UserAccessToken userAccessToken) {
        if (userAccessToken.isEnabled()){
            // role 가져오기 -> userAccessToken.getAuthorities().toString() => [ROLE_MANAGER] 잘라서 쓰기
            return GenericResponse.ok(userAccessToken.getTokenConnector());
        }
        
        return GenericResponse.ok();
    }
    
}
