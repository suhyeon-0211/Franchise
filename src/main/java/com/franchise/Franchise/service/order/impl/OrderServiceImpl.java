package com.franchise.Franchise.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.dto.OrderTotalSale;
import com.franchise.Franchise.dto.OrderTotalSalePerDay;
import com.franchise.Franchise.dto.ProductSaleAndCnt;
import com.franchise.Franchise.dto.ProductSalePerDay;
import com.franchise.Franchise.entity.Order;
import com.franchise.Franchise.entity.OrderDetail;
import com.franchise.Franchise.entity.ProductDetailPrice;
import com.franchise.Franchise.enums.Role;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionDefinedReason;
import com.franchise.Franchise.mapper.OrderMapper;
import com.franchise.Franchise.repository.OrderDetailRepository;
import com.franchise.Franchise.repository.OrderRepository;
import com.franchise.Franchise.repository.ProductDetailPriceRepository;
import com.franchise.Franchise.request.RequestOrder;
import com.franchise.Franchise.request.RequestOrderDetail;
import com.franchise.Franchise.request.RequestOrderStatus;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.response.order.ResponseOrder;
import com.franchise.Franchise.response.order.ResponseOrderCompleted;
import com.franchise.Franchise.response.order.ResponseOrderDetail;
import com.franchise.Franchise.response.order.ResponseOrderStatus;
import com.franchise.Franchise.service.order.OrderService;
import com.franchise.Franchise.util.SetDate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    
    private final OrderRepository orderRepository;
    private final ProductDetailPriceRepository productDetailPriceRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;

    @Override
    public ResponseOrder addOrder(RequestOrder request) {
        // 결제를 등록하면서 상품 디테일의 재고 또한 update시켜 줘야 함.
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<ProductDetailPrice> productDetailPriceList = new ArrayList<>();
        List<ResponseOrderDetail> responseOrderDetailList = new ArrayList<>();
        int totalPrice = 0;
        int totalDiscount = 0;
        for (RequestOrderDetail detail : request.getRequestOrderDetailList()) {
            // 이렇게 하면 마지막에 id가 없어서 오류가 난 경우에 이미 product_detail_price는 update가 진행되고 난 후
            // 결제가 들어갈때까지는 product_detail_price는 update가 되면 안됨
            // 실제 있는 상품인지 체크
            ProductDetailPrice productDetailPrice = productDetailPriceRepository.findById(detail.getProductPriceId())
                            .orElseThrow(() -> new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_PRODUCT));
            // 상품의 재고 확인
            int productAmount = productDetailPrice.getProductAmount();
            if (productAmount < detail.getOrderAmount()) {
                throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_VALID_ORDER);
            }
            productDetailPrice.setProductAmount(productAmount - detail.getOrderAmount());
            // 상품의 상태 확인
            if (!productDetailPrice.getState().equals(Status.ACTIVATE)) {
                throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_VALID_PRODUCT_STATE);
            }
            // 상세 주문에 대한 상품 가격 총합 계산
            int orderDetailPrice = (productDetailPrice.getPrice() * detail.getOrderAmount()) - detail.getDiscountPrice();
            totalPrice += orderDetailPrice;
            totalDiscount += detail.getDiscountPrice();
            
            OrderDetail orderDetail = new OrderDetail(detail, orderDetailPrice);
            orderDetailList.add(orderDetail);

            responseOrderDetailList.add(new ResponseOrderDetail(orderDetail));
            productDetailPriceList.add(productDetailPrice);
        }
        Order order = orderRepository.save(new Order(request, orderDetailList, totalPrice, totalDiscount));
        productDetailPriceRepository.saveAll(productDetailPriceList);
        return new ResponseOrder(order, responseOrderDetailList);
    }

    @Override
    public List<ResponseOrder> getOrderList(UserAccessToken token) {
        List<Order> orderList = new ArrayList<>();
        if (token.getUserRole().equals(Role.ADMIN)) {
            orderList = orderRepository.findAllByStateIsNot(Status.DELETE);
        } else {
            Long storeId = token.getTokenConnector().getUser().getStoreId();
            orderList = orderRepository.findByStoreIdAndStateIsNot(storeId, Status.DELETE);
        }
        return getResponseOrderList(orderList);
    }
    

    //조회한 결제정보를 ResponseOrder로 Convert
    public List<ResponseOrder> getResponseOrderList(List<Order> orderList) {
        List<ResponseOrder> responseOrderList = new ArrayList<>();
        for (Order order : orderList) {
            List<ResponseOrderDetail> responseOrderDetailList = new ArrayList<>();
            if (order.getOrderDetailList().isEmpty()) {
                //throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_ORDER);
                continue;
            }
            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                if(orderDetail.getState().equals(Status.DELETE)) {
                    continue;
                }
                responseOrderDetailList.add(new ResponseOrderDetail(orderDetail));
            }
            responseOrderList.add(new ResponseOrder(order, responseOrderDetailList));
        }
        return responseOrderList;
    }

    @Override
    public ResponseOrderStatus changeStatusOrder(RequestOrderStatus requestOrderStatus, UserAccessToken token) {
        OrderDetail orderDetail = orderDetailRepository.findById(requestOrderStatus.getId()).orElseThrow(() -> new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_ORDER));
        if(!token.getUserRole().equals(Role.ADMIN) &&
            !token.getTokenConnector().getUser().getStoreId().equals(orderDetail.getOrder().getStoreId())) {
                throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_ADMIN_USER);
        }
        orderDetail.setState(Status.valueOf(requestOrderStatus.getStatus()));
        OrderDetail tempOrderDetail = orderDetailRepository.save(orderDetail);
        // 결재 취소 시 재고 다시 돌려 놓기
        ProductDetailPrice productDetailPrice = productDetailPriceRepository.findById(orderDetail.getProductPriceId()).orElseThrow(() -> new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_PRODUCT));
        productDetailPriceRepository.save(productDetailPrice.toBuilder()
                                        .productAmount(productDetailPrice.getProductAmount() + orderDetail.getOrderAmount()).build());
        // 총 결재 금액 바꾸기
        Order order = orderDetail.getOrder();
        orderRepository.save(order.toBuilder().totalPrice(order.getTotalPrice() - orderDetail.getPrice()).totalDiscount(order.getTotalDiscount() - orderDetail.getDiscountPrice()).build());
        return new ResponseOrderStatus(tempOrderDetail.getOrder(), new ResponseOrderDetail(tempOrderDetail));
    }

    @Override
    public ResponseOrderCompleted getOrderCompleted(RequestStatistics request, UserAccessToken token) {
        RequestStatistics tempRequest = SetDate.setDate(request, token);
        List<OrderTotalSale> orderTotalSaleList = getOrderTotalSaleList(tempRequest.getStartDate(), tempRequest.getEndDate(), token);
        List<OrderTotalSalePerDay> orderTotalSalePerDayList = getOrderTotalSalePerDayList(tempRequest.getStartDate(), tempRequest.getEndDate(), token);
        return new ResponseOrderCompleted(orderTotalSaleList, orderTotalSalePerDayList);
    }

    public List<OrderTotalSale> getOrderTotalSaleList(String startDate, String endDate, UserAccessToken token) {
        return token.getUserRole().equals(Role.ADMIN) ? orderMapper.selectOrderTotalSaleList(startDate, endDate) 
                                                    : orderMapper.selectOrderTotalSaleListByStoreId(startDate, endDate, token.getTokenConnector().getUser().getStoreId());
    }

    public List<OrderTotalSalePerDay> getOrderTotalSalePerDayList(String startDate, String endDate, UserAccessToken token) {
        return token.getUserRole().equals(Role.ADMIN) ? orderMapper.selectOrderTotalSaleListPerDay(startDate, endDate)
                                                    : orderMapper.selectOrderTotalSaleListPerDayByStoreId(startDate, endDate, token.getTokenConnector().getUser().getStoreId());
    }
}
