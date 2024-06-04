package com.franchise.Franchise.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.request.RequestOrder;
import com.franchise.Franchise.request.RequestOrderStatus;
import com.franchise.Franchise.request.RequestStatistics;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.order.ResponseOrder;
import com.franchise.Franchise.response.order.ResponseOrderCompleted;
import com.franchise.Franchise.response.order.ResponseOrderStatus;
import com.franchise.Franchise.service.order.OrderService;
import com.franchise.Franchise.valid.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;



@Tag(name = "ORDER", description = "결제 관리 및 조회")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    @Tag(name = "ORDER")
    @Operation(summary = "결제 등록", description = "결제를 등록합니다.")
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<ResponseOrder>> addOrder(final @Validated(ValidationSequence.class) @RequestBody RequestOrder request) {
        return GenericResponse.ok(orderService.addOrder(request));
    }
    
    @Tag(name = "ORDER")
    @Operation(summary = "결제 리스트 조회", description = "결제 리스트를 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<GenericResponse<List<ResponseOrder>>> getOrderList(final @AuthenticationPrincipal UserAccessToken userAccessToken) {
        return GenericResponse.ok(orderService.getOrderList(userAccessToken));
    }
    
    @Tag(name = "ORDER")
    @Operation(summary = "부분 결제 취소", description = "토큰으로 들어온 유저 정보를 조회합니다.")
    @PostMapping("/change-status")
    public ResponseEntity<GenericResponse<ResponseOrderStatus>> changeStatusOrder(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                            ,@Valid RequestOrderStatus request) {
        return GenericResponse.ok(orderService.changeStatusOrder(request, userAccessToken));
    }

    @Tag(name = "ORDER")
    @Operation(summary = "결제 통계", description = "결졔 통계를 조회합니다.")
    @GetMapping("/statistics")
    public ResponseEntity<GenericResponse<ResponseOrderCompleted>> getOrderCompleted(final @AuthenticationPrincipal UserAccessToken userAccessToken
                                                                            ,@Valid RequestStatistics request) {
        return GenericResponse.ok(orderService.getOrderCompleted(request, userAccessToken));
    }
    
}
