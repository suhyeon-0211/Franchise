package com.franchise.Franchise.response.user;

import com.franchise.Franchise.entity.Connector;

import lombok.Data;

@Data
public class ResponseConnector {
    private Long id;
    private Long storeId;
    private String email;
    private String password;
    private String name;

    public ResponseConnector(Connector connector) {
        this.id = connector.getId();
        this.storeId = (long) connector.getUser().getStoreId();
        this.email = connector.getEmail();
        this.password = connector.getPassword();
        this.name = connector.getUser().getName();
    }
}
