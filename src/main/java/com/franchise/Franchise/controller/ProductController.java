package com.franchise.Franchise.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.dto.ProductSale;
import com.franchise.Franchise.entity.Product;
import com.franchise.Franchise.mapper.ProductMapper;
import com.franchise.Franchise.request.RequestProduct;
import com.franchise.Franchise.request.RequestProductStatus;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.product.ResponseProduct;
import com.franchise.Franchise.service.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;




@Tag(name = "PRODUCT", description = "상품 관리 및 조회")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    
    @PostMapping("/add")
    @Tag(name = "PRODUCT")
    @Operation(summary = "상품 추가", description = "상품을 추가합니다.")
    public ResponseEntity<GenericResponse<ResponseProduct>> addProduct(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                        ,@Valid RequestProduct requestProduct) {
        return GenericResponse.ok(productService.addProduct(requestProduct, userAccessToken));
    }

    @GetMapping("/list")
    @Tag(name = "PRODUCT")
    @Operation(summary = "상품 리스트", description = "상품 리스트를 조회합니다.")
    public ResponseEntity<GenericResponse<List<ResponseProduct>>> getProductList(final @AuthenticationPrincipal UserAccessToken userAccessToken) {
        return GenericResponse.ok(productService.getProductList(userAccessToken));
    }

    @PostMapping("/change-status")
    @Tag(name = "PRODUCT")
    @Operation(summary = "상품 상태 변경", description = "상품의 상태를 변경합니다.")
    public ResponseEntity<GenericResponse<ResponseProduct>> changeStatusProduct(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                            ,@Valid RequestProductStatus requestProductStatus) {
        return GenericResponse.ok(productService.changeStatusProduct(requestProductStatus, userAccessToken));
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<GenericResponse<ProductSale>> getStatistics(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                        , RequestStatistics requestStatistics) {
        // 상품 통계는 삭제된 상품도 조회합니다.
        return GenericResponse.ok(productService.getStatistics(requestStatistics, userAccessToken));
    }

    @GetMapping("/test")
    public List<Product> test() {
        return productMapper.selectProductList();
    }
    
    
}
