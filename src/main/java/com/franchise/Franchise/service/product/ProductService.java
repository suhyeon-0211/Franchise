package com.franchise.Franchise.service.product;

import java.util.List;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.dto.ProductSale;
import com.franchise.Franchise.request.RequestProduct;
import com.franchise.Franchise.request.RequestProductStatus;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.response.product.ResponseProduct;

public interface ProductService {
    ResponseProduct addProduct(RequestProduct request, UserAccessToken token);
    List<ResponseProduct> getProductList(UserAccessToken token);
    ResponseProduct changeStatusProduct(RequestProductStatus request, UserAccessToken token);
    ProductSale getStatistics(RequestStatistics request, UserAccessToken token);
}
