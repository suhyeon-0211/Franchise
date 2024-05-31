package com.franchise.Franchise.service.product.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.dto.ProductSale;
import com.franchise.Franchise.dto.ProductSaleAndCnt;
import com.franchise.Franchise.dto.ProductSalePerDay;
import com.franchise.Franchise.entity.Product;
import com.franchise.Franchise.entity.ProductDetailPrice;
import com.franchise.Franchise.enums.Role;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionDefinedReason;
import com.franchise.Franchise.mapper.ProductDetailPriceMapper;
import com.franchise.Franchise.repository.ProductDetailPriceRepository;
import com.franchise.Franchise.repository.ProductRepository;
import com.franchise.Franchise.request.RequestProduct;
import com.franchise.Franchise.request.RequestProductStatus;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.response.product.ResponseProduct;
import com.franchise.Franchise.service.product.ProductService;
import com.franchise.Franchise.util.SetDate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductDetailPriceRepository productDetailPriceRepository;
    private final ProductDetailPriceMapper productDetailPriceMapper;


    @Override
    public ResponseProduct addProduct(RequestProduct request, UserAccessToken token) {
        // ADMIN 은 상품을 추가할 때 storeID를 입력해 줘야 함
        // 이미 있는 제품이라면 detail만 추가
        if (token.getUserRole().equals(Role.ADMIN)) {
            if (request.getStoreId() == null) {
                throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_STORE_ID);
            }
        } else {
            request.setStoreId(token.getTokenConnector().getUser().getStoreId());

        }
        ProductDetailPrice productDetailPrice = null;
        Optional<Product> product = productRepository.findByStoreIdAndName(request.getStoreId(), request.getName());
    
        if (product.isPresent()) {
            Optional<ProductDetailPrice> tempProductDetailPrice = productDetailPriceRepository.findByProductIdAndProductUnit(product.get().getId(), request.getProductUnit());
            if (tempProductDetailPrice.isPresent()) {
                // 기존에 같은 detailPrice가 있다면 가격, 재고, 상태만 변경해서 update
                tempProductDetailPrice.get().setPrice(request.getPrice());
                tempProductDetailPrice.get().setProductAmount(request.getProductAmount());
                tempProductDetailPrice.get().setState(Status.ACTIVATE);
                productDetailPrice = productDetailPriceRepository.save(tempProductDetailPrice.get());
            } else {
                productDetailPrice = productDetailPriceRepository.save(new ProductDetailPrice(request, product.get()));
            }
        } else {
            Product temProduct = new Product(request, token);
            productDetailPrice = productDetailPriceRepository.save(new ProductDetailPrice(request, temProduct));
        }
        return new ResponseProduct(productDetailPrice);
    }


    @Override
    public List<ResponseProduct> getProductList(UserAccessToken token) {
        List<Product> productList = new ArrayList<>();
        if (token.getUserRole().equals(Role.ADMIN)) {
            productList = productRepository.findAllByStateIsNot(Status.DELETE);
        } else {
            Long storeId = token.getTokenConnector().getUser().getStoreId();
            //Optional<Product> product = productRepository.findByStoreId(storeId);
            productList = productRepository.findByStoreIdAndStateIsNot(storeId, Status.DELETE);
        }
        return getResponseProductList(productList);
    }

    // 조회한 상품정보를 ResponseProduct로 Convert
    public List<ResponseProduct> getResponseProductList(List<Product> productList) {
        List<ResponseProduct> responseProductList = new ArrayList<>();
        for (Product product : productList) {
            for (ProductDetailPrice productDetailPrice : product.getProductDetailPriceList()) {
                if(productDetailPrice.getState().equals(Status.DELETE)) {
                    continue;
                }
                ResponseProduct responseProduct = new ResponseProduct(productDetailPrice);
                responseProductList.add(responseProduct);
            }
        }
        return responseProductList;
    }
    
    // 상품 상태 변경 (활성화, 비활성화, 삭제)
    @Override
    public ResponseProduct changeStatusProduct(RequestProductStatus request, UserAccessToken token) {
        Optional<ProductDetailPrice> productDetailPrice = productDetailPriceRepository.findById(request.getId());
        // admin이라면 storeId와 상관없이 state변경 가능
        if (!token.getUserRole().equals(Role.ADMIN) && 
                !token.getTokenConnector().getUser().getStoreId().equals(productDetailPrice.get().getProduct().getStoreId())) {
            // admin이 아니면서 토큰에서 추출한 storeId와 productDetailPrice에서 추출한 storeId가 다르면 Exception
            throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_ADMIN_USER);
        }
        productDetailPrice.get().setState(Status.valueOf(request.getStatus()));
        ProductDetailPrice tempProductDetailPrice = productDetailPriceRepository.save(productDetailPrice.get());
        return new ResponseProduct(tempProductDetailPrice);
    }

    // 상품 등록, 비활성, 삭제 통계
    @Override
    public ProductSale getStatistics(RequestStatistics request, UserAccessToken token){
        // admin이라면 모든 정보 출력
        // admin이 아니라면 
        // 1. 입력 데이터의 유효성 체크
        // 1-1. 끝나는 날짜만 있다면 throw exception
        // 1-2. unit은 적당한 값이 들어왔는지 (valid)
        // 2. 입력된 날짜에 대해 통계 조회
        // 2-1. 시작날짜만 있다면 그날부터 오늘까지 조회
        // 2-2. 시작날짜에 DAY라면 그 날 조회
        // 2-3. 시작날짜에 WEEK, MONTH, YEAR 모두 동일하게 동작
        // 2-4. 시작날짜 마지막날짜 둘 다 있다면 그 사이 조회
        RequestStatistics tempRequest = SetDate.setDate(request, token);
        List<ProductSaleAndCnt> productSaleAndCntList = getProductSaleAndCntList(tempRequest.getStartDate(), tempRequest.getEndDate(), token);
        List<ProductSalePerDay> productSalePerDayList = getProductSalePerDayList(tempRequest.getStartDate(), tempRequest.getEndDate(), token);
        return new ProductSale(productSaleAndCntList, productSalePerDayList);
    }

    public List<ProductSaleAndCnt> getProductSaleAndCntList(String startDate, String endDate, UserAccessToken token) {
        return token.getUserRole().equals(Role.ADMIN) ? productDetailPriceMapper.selectProductDetailSaleAndCnt(startDate, endDate) 
                                                    : productDetailPriceMapper.selectProductDetailSaleAndCntByStoreId(startDate, endDate, token.getTokenConnector().getUser().getStoreId());
    }

    public List<ProductSalePerDay> getProductSalePerDayList(String startDate, String endDate, UserAccessToken token) {
        return token.getUserRole().equals(Role.ADMIN) ? productDetailPriceMapper.selectProductDetailSalePerDay(startDate, endDate) 
                                                    : productDetailPriceMapper.selectProductDetailSalePerDayByStoreId(startDate, endDate, token.getTokenConnector().getUser().getStoreId());
    }

    // public ProductSale getDetailStatistics(RequestProductStatistics request, UserAccessToken token) {
    //     if (request.getStartDate().isEmpty()) {
    //         throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_DATE);
    //     } else {
    //         int dayAmount = 0;
    //         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //         try {
    //             Date date = formatter.parse(request.getStartDate());
    //         } catch (ParseException e) {
    //             e.printStackTrace();
    //         }
    //         Calendar cal = Calendar.getInstance();
    //         if (request.getUnit().equals(DatePick.DAY)) {
    //             // 시작날짜만 조회
    //             dayAmount = 0;
    //         } else if (request.getUnit().equals(DatePick.WEEK)) {
    //             // 시작날짜 기준 7일 조회
    //             dayAmount = 7;
    //         } else if (request.getUnit().equals(DatePick.MONTH)) {
    //             // 시작날짜 기준 1달 조회
    //             dayAmount = 30;
    //         } else if (request.getUnit().equals(DatePick.YEAR)) {
    //             // 시작날짜 기준 1년 조회
    //             dayAmount = 365;
    //         } else if (!request.getEndDate().isEmpty()) {
    //             // 시작날짜부터 마지막날까지 조회
    //             List<ProductSaleAndCnt> productSaleAndCntList = getProductSaleAndCntList(request.getStartDate(), request.getEndDate(), token);
    //             List<ProductSalePerDay> productSalePerDayList = getProductSalePerDayList(request.getStartDate(), request.getEndDate(), token);
    //             return new ProductSale(productSaleAndCntList, productSalePerDayList);
    //         } else if (request.getEndDate().isEmpty()) {
    //             // 시작날짜부터 오늘까지 조회
    //             List<ProductSaleAndCnt> productSaleAndCntList = getProductSaleAndCntList(request.getStartDate(), formatter.format(LocalDate.now()), token);
    //             List<ProductSalePerDay> productSalePerDayList = getProductSalePerDayList(request.getStartDate(), formatter.format(LocalDate.now()), token);
    //             return new ProductSale(productSaleAndCntList, productSalePerDayList);
    //         } else {
    //             throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_DATE);
    //         }
    //         cal.add(Calendar.DATE, -dayAmount);
    //         String endDate = formatter.format(cal.getTime());
    //         List<ProductSaleAndCnt> productSaleAndCntList = getProductSaleAndCntList(request.getStartDate(), endDate, token);
    //         List<ProductSalePerDay> productSalePerDayList = getProductSalePerDayList(request.getStartDate(), endDate, token);
    //         return new ProductSale(productSaleAndCntList, productSalePerDayList);
    //     }
    // }

    
}