package com.franchise.Franchise.entity;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franchise.Franchise.enums.Role;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.request.RequestConnector;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("사용자 테이블")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Schema(description = "매장 번호", example = "")
    private Long storeId;
    @Schema(description = "이름", example = "")
    private String name;
    @Schema(description = "주소", example = "")
    private String address;

    @Schema(description = "역할", example = "ADMIN, MANAGER")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Schema(description = "상태", example = "ACTIVATE, DEACTIVATE...")
    @Enumerated(EnumType.STRING)
    private Status state;

    @Schema(description = "생성 날짜", example = "")
    @UpdateTimestamp
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    @Schema(description = "수정 날짜", example = "")
    @UpdateTimestamp
    private Date updatedAt;

    public User(RequestConnector connector) {
        this.storeId = connector.getStoreId();
        this.name = connector.getName();
        this.address = connector.getAddress();
        this.role = Role.valueOf(connector.getRole());
        this.state = Status.ACTIVATE;
    }

    public User(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Object, Object> map = objectMapper.convertValue(object, Map.class);
        this.id = Long.parseLong(map.get("id").toString());
        this.storeId = Long.parseLong(map.get("storeId").toString());
        this.name = map.get("name").toString();
        this.address = map.get("address").toString();
        this.role = Role.valueOf(map.get("role").toString());
        this.state = Status.valueOf(map.get("state").toString());
    }
}
