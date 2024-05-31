package com.franchise.Franchise.token;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.franchise.Franchise.entity.Connector;
import com.franchise.Franchise.repository.ConnectorRepository;
import com.franchise.Franchise.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenMaker {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ConnectorRepository connectorRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken tokenMaker(Connector connector) {
        // 1. id(not email, PK) + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(connector.getId(), connector.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        long now = (new Date()).getTime();
        Connector tempConnector = connector.toBuilder().accessToken(jwtToken.getAccessToken()).refreshToken(jwtToken.getRefreshToken()).updatedAt(new Date(now)).build();
        connectorRepository.save(tempConnector);
        return jwtToken;
    }
}
