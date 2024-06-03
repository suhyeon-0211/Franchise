package com.franchise.Franchise.service.sign.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.entity.Connector;
import com.franchise.Franchise.entity.User;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionDefinedReason;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionReason;
import com.franchise.Franchise.repository.ConnectorRepository;
import com.franchise.Franchise.repository.UserRepository;
import com.franchise.Franchise.request.RequestConnector;
import com.franchise.Franchise.request.SigninRequest;
import com.franchise.Franchise.response.user.ResponseConnector;
import com.franchise.Franchise.service.sign.SignService;
import com.franchise.Franchise.token.JwtToken;
import com.franchise.Franchise.token.JwtTokenMaker;
import com.franchise.Franchise.util.SHA256;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService{

    private final ConnectorRepository connectorRepository;
    private final UserRepository userRepository;
    private final JwtTokenMaker jwtTokenMaker;

    @Override
    public ResponseConnector signup(RequestConnector request) {
        if (existsEmail(request.getEmail())) {
            throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.EXISTS_USER); 
        }
        request.setPassword(SHA256.simple(request.getPassword()));
        //User user = userRepository.save(new User(request));
        User user = new User(request);
        Connector connector = connectorRepository.save(new Connector(request, user));
        return new ResponseConnector(connector);
    }

    public boolean existsEmail(String email) {
        return connectorRepository.existsByEmail(email);
    }

    @Override
    public JwtToken signin(SigninRequest request) {
        Optional<Connector> connector = connectorRepository.findByEmailAndPassword(request.getEmail(), SHA256.simple(request.getPassword()));
        if (!connector.isPresent() || connector.get().getState().equals(Status.DELETE)) {
            throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_USER);
        }
        if (connector.get().getState().equals(Status.DEACTIVATE)) {
            throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_MATCH_ID_EXCEPTION);
        }
        JwtToken jwtToken = jwtTokenMaker.tokenMaker(connector.get());
        return jwtToken;
    }

    @Override
    public void logout(UserAccessToken userAccessToken) {
        Long now = (new Date()).getTime();
        Optional<Connector> connector = connectorRepository.findById(userAccessToken.getTokenConnector().getId());
        Connector tempConnector = connector.get().toBuilder().accessToken(null).refreshToken(null).build();
        connectorRepository.save(tempConnector);
    }
    
}
