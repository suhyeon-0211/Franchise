package com.franchise.Franchise.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.request.RequestProduct;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ApiModel("상품 테이블")
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Schema(description = "매장 번호", example = "")
    private Long storeId;
    @Schema(description = "상품명", example = "")
    private String name;
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

    @Schema(description = "상품 가격 상세 테이블 매핑", example = "")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductDetailPrice> productDetailPriceList;

    public Product(RequestProduct request, UserAccessToken userAccessToken) {
        this.storeId = request.getStoreId();
        this.name = request.getName();
        this.state = Status.ACTIVATE;
    }
    
}
