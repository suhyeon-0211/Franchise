package com.franchise.Franchise.domain;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franchise.Franchise.entity.TokenConnector;
import com.franchise.Franchise.enums.Role;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiOperation(
    value = "User Access Token",
    notes = "사용자 토큰 정보"
)
public class UserAccessToken extends User{
    @ApiParam(
        name = "userId",
        type = "String",
        value = "userId",
        example = "user",
        required = true
    )
    private TokenConnector tokenConnector;

    public UserAccessToken(TokenConnector tokenConnector, UserDetails user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.tokenConnector = tokenConnector;
    }

    public UserAccessToken(Object object, UserDetails user){
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        TokenConnector tempConnector = new TokenConnector();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Object, Object> map = objectMapper.convertValue(object, Map.class);
        tempConnector.setId(Long.parseLong(map.get("id").toString()));
        tempConnector.setEmail(map.get("email").toString());
        tempConnector.setPassword(map.get("password").toString());
        com.franchise.Franchise.entity.User tempUser = new com.franchise.Franchise.entity.User(map.get("user"));
        tempConnector.setUser(tempUser);
        this.tokenConnector = tempConnector;

    }

    public Role getUserRole() {
        return this.getTokenConnector().getUser().getRole();
    }
}
