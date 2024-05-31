package com.franchise.Franchise.service.sign;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.request.RequestConnector;
import com.franchise.Franchise.request.SigninRequest;
import com.franchise.Franchise.response.user.ResponseConnector;
import com.franchise.Franchise.token.JwtToken;

public interface SignService {
    ResponseConnector signup(RequestConnector request);
    JwtToken signin(SigninRequest request);
    void logout(UserAccessToken userAccessToken);
}
