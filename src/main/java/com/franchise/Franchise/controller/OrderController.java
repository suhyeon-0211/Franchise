package com.franchise.Franchise.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.request.RequestOrder;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.request.RequestOrderStatus;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.order.ResponseOrder;
import com.franchise.Franchise.response.order.ResponseOrderCompleted;
import com.franchise.Franchise.response.order.ResponseOrderStatus;
import com.franchise.Franchise.service.order.OrderService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    @PostMapping("/order")
    public ResponseEntity<GenericResponse<ResponseOrder>> addOrder(final @Valid @RequestBody RequestOrder request) {
        //TODO: process POST request
        
        return GenericResponse.ok(orderService.addOrder(request));
    }
    
    @GetMapping("/list")
    public ResponseEntity<GenericResponse<List<ResponseOrder>>> getOrderList(final @AuthenticationPrincipal UserAccessToken userAccessToken) {
        return GenericResponse.ok(orderService.getOrderList(userAccessToken));
    }
    
    @PostMapping("/change-status")
    public ResponseEntity<GenericResponse<ResponseOrderStatus>> changeStatusOrder(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                            ,@Valid RequestOrderStatus request) {
        return GenericResponse.ok(orderService.changeStatusOrder(request, userAccessToken));
    }

    @GetMapping("/statistics")
    public ResponseEntity<GenericResponse<ResponseOrderCompleted>> getOrderCompleted(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                            ,@Valid RequestStatistics request) {
        return GenericResponse.ok(orderService.getOrderCompleted(request, userAccessToken));
    }
    
}
