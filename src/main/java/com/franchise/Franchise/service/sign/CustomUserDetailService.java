package com.franchise.Franchise.service.sign;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.entity.TokenConnector;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionDefinedReason;
import com.franchise.Franchise.repository.TokenConnectorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{
    
    private final TokenConnectorRepository tokenConnectorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // Optional<TokenUserEntity> user = tokenUserRepository.findById(Long.valueOf(id));
        // user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
        // UserAccessToken userAccessToken = new UserAccessToken(user.get());
        // return userAccessToken;
        return tokenConnectorRepository.findById(Long.valueOf(id))
            .map(this::createUserDetails)
            .orElseThrow(() -> new BusinessLogicException(BusinessLogicExceptionDefinedReason.EXISTS_USER));
    }

    public UserDetails createUserDetails(TokenConnector tokenConnector) {
        UserDetails userDetails = User.builder()
            .username(String.valueOf(tokenConnector.getId()))
            .password(passwordEncoder.encode(tokenConnector.getPassword()))
            .roles(tokenConnector.getUser().getRole().toString())
            .build();
        UserAccessToken userAccessToken = new UserAccessToken(tokenConnector, userDetails);
        return userAccessToken;
        
        
    }
}
