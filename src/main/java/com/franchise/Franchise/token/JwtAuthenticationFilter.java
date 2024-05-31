package com.franchise.Franchise.token;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.franchise.Franchise.entity.Connector;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.repository.ConnectorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final ConnectorRepository connectorRepository;
    private final JwtTokenMaker jwtTokenMaker;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. Request Header에서 JWT 토큰 추출
        String accessToken = resolveToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);
        
        // 2. validateToken으로 토큰 유효성 검사
        if (accessToken != null) {
            Optional<Connector> connectorFindByAccessToken = connectorRepository.findByAccessToken(accessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            Optional<Connector> connectorFindById = connectorRepository.findById(Long.parseLong(authentication.getName()));
            if (connectorFindByAccessToken.isPresent() && connectorFindById.isPresent() 
            && connectorFindByAccessToken.toString().equals(connectorFindById.toString()) ) {
                try {
                    jwtTokenProvider.validateToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (BusinessLogicException e) {
                    log.info("만료된 AccessToken입니다.");
                    try {
                        if(refreshToken != null && refreshToken.equals(connectorFindById.get().getRefreshToken())) {
                            jwtTokenProvider.validateToken(refreshToken);
                            JwtToken jwtToken = jwtTokenMaker.tokenMaker(connectorFindById.get());
                            log.info(jwtToken.toString());
                        }
                    } catch (BusinessLogicException e1) {
                        log.info("만료된 refreshToken입니다. 다시 로그인해주세요");
                    } catch (NullPointerException e2) {
                        log.info("발행된 토큰이 없습니다. 다시 로그인해주세요");
                    } 

                }
                // if (jwtTokenProvider.validateToken(accessToken)) {
                //     SecurityContextHolder.getContext().setAuthentication(authentication);
                // } else if (!jwtTokenProvider.validateTokenExpired(accessToken) && refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                //     // accessToken 이 만료되었고 refreshToken이 유효하다면 accessToken 생성
                //     JwtToken jwtToken = jwtTokenMaker.tokenMaker(userFindById.get());
                //     log.info(jwtToken.toString());
                // }
            }
        } 
        // if (accessToken != null && jwtTokenProvider.validateToken(accessToken) && user.isPresent()) {
        //     // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
        //     Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        //     authentication.getPrincipal();
        //     SecurityContextHolder.getContext().setAuthentication(authentication);
        // } else if (!jwtTokenProvider.validateTokenExpired(accessToken) && refreshToken != null && user.isPresent()) {
        //     // accessToken이 만료되었고 refreshToken이 유효하다면 accessToken 재발행
        //     if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
        //         jwtTokenMaker.tokenMaker(user.get());
        //     }
        // }
        chain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
