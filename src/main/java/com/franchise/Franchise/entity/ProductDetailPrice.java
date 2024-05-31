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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

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
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("상품 가격 상세 테이블")
@Table(name = "product_detail_price")
public class ProductDetailPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long productId;
    @Schema(description = "가격", example = "")
    private int price;
    @Schema(description = "상품 단위", example = "낱개, kg ...")
    private String productUnit;
    @Schema(description = "상품 재고", example = "")
    private int productAmount;

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

    @Schema(description = "상품 테이블 매핑", example = "")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="productId")
    private Product product;

    public ProductDetailPrice(RequestProduct requestProduct, Product product) {
        //this.productId = productId;
        this.price = requestProduct.getPrice();
        this.productUnit = requestProduct.getProductUnit();
        this.productAmount = requestProduct.getProductAmount();
        this.state = Status.ACTIVATE;
        this.product = product;
    }

    public ProductDetailPrice(RequestProduct reqeustProduct, ProductDetailPrice productDetailPrice) {
        
    }

}
