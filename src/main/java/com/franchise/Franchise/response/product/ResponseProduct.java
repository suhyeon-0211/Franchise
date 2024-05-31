package com.franchise.Franchise.response.product;

import com.franchise.Franchise.entity.Product;
import com.franchise.Franchise.entity.ProductDetailPrice;
import com.franchise.Franchise.enums.Status;

import lombok.Data;

@Data
public class ResponseProduct {
    private Long productId;
    private Long storeId;
    private String name;
    private int price;
    private String productUnit;
    private int productAmount;
    private Status state;

    public ResponseProduct(ProductDetailPrice productDetailPrice) {
        this.productId = productDetailPrice.getId();
        this.storeId = productDetailPrice.getProduct().getStoreId();
        this.name = productDetailPrice.getProduct().getName();
        this.price = productDetailPrice.getPrice();
        this.productUnit = productDetailPrice.getProductUnit();
        this.productAmount = productDetailPrice.getProductAmount();
        this.state = productDetailPrice.getState();
    }

    public ResponseProduct(Product product) {
        //this.storeId = product.getProductDetailPriceList();
    }
}
