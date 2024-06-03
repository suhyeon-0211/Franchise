package com.franchise.Franchise.service.order;

import java.text.ParseException;
import java.util.List;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.request.RequestOrder;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.request.RequestOrderStatus;
import com.franchise.Franchise.response.order.ResponseOrder;
import com.franchise.Franchise.response.order.ResponseOrderCompleted;
import com.franchise.Franchise.response.order.ResponseOrderStatus;

public interface OrderService {
    ResponseOrder addOrder(RequestOrder request);
    List<ResponseOrder> getOrderList(UserAccessToken token);
    ResponseOrderStatus changeStatusOrder(RequestOrderStatus request, UserAccessToken token);
    ResponseOrderCompleted getOrderCompleted(RequestStatistics request, UserAccessToken token);
}
