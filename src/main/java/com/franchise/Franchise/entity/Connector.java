package com.franchise.Franchise.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.UpdateTimestamp;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@ApiModel("로그인 커넥터 테이블")
@Table(name = "connector")
public class Connector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long userId;
    @Schema(description = "로그인 Id", example = "xxx@xxx.com")
    private String email;
    @Schema(description = "비밀번호", example = "")
    private String password;
    @Schema(description = "엑세스토큰", example = "")
    private String accessToken;
    @Schema(description = "리프레시토큰", example = "")
    private String refreshToken;

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

    @Schema(description = "user 테이블 매핑", example = "")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", unique = true)
    //@Transient
    private User user;

    public Connector(RequestConnector request, User user) {
        //this.userId = user.getId();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.user = user;
        this.state = Status.ACTIVATE;

    }
}
