package com.franchise.Franchise.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum BusinessLogicExceptionDefinedReason implements BusinessLogicExceptionReason{
    REQUIRED_VALUE(HttpStatus.BAD_REQUEST, 0010, "필수값을 입력하지 않았습니다.", "com.sfull.forward.golf.required"),
    NOT_FOUND_VER(HttpStatus.NOT_FOUND, 1000, "버전을 찾을 수 없습니다.", "com.sfull.forward.golf.ver.not.found"),
    DIFF_VER_FORCE(HttpStatus.BAD_REQUEST, 1001, "업데이트 버전이 있습니다.\r\n업데이트 하시겠습니까?", "com.sfull.forward.golf.ver.diff.force"),
    DIFF_VER(HttpStatus.BAD_REQUEST, 1002, "업데이트 버전이 있습니다.\r\n업데이트 하시겠습니까?", "com.sfull.forward.golf.ver.diff"),
    NOT_AVAILABLE_ISSUED_USING_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, 1602, "신규 토큰이 발급 가능한 상태가 아닙니다.", "exception.not.available.issued.using.refresh.token"),
    NOT_FOUND_EVENT(HttpStatus.NOT_FOUND, 2000, "이벤트를 찾을 수 없습니다.", "com.sfull.forward.golf.event.not.found"),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, 3000, "상품을 찾을 수 없습니다.", "com.sfull.forward.golf.product.not.found"),
    NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND, 4000, "공지를 찾을 수 없습니다.", "com.sfull.forward.golf.notice.not.found"),
    NOT_FOUND_BANNER(HttpStatus.NOT_FOUND, 5000, "배너를 찾을 수 없습니다.", "com.sfull.forward.golf.banner.not.found"),
    NOT_FOUND_AD(HttpStatus.NOT_FOUND, 6000, "광고를 찾을 수 없습니다.", "com.sfull.forward.golf.ad.not.found"),
    EXISTS_USER(HttpStatus.BAD_REQUEST, 7000, "이미 가입한 유저 정보입니다.", "com.sfull.forward.golf.user.exists"),
    EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, 7001, "이미 있는 닉네임 정보입니다.", "com.sfull.forward.golf.user.exists.nickname"),
    EXISTS_LOGIN_ID(HttpStatus.BAD_REQUEST, 7002, "이미 있는 로그인 ID 정보입니다.", "com.sfull.forward.golf.user.exists.login.id"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 7003, "계정정보가 존재하지 않습니다.", "com.sfull.forward.golf.user.not.found"),
    ERROR_ENCRYPT(HttpStatus.BAD_REQUEST, 7004, "암호화 에러", "com.sfull.forward.golf.user.error.encrypt"),
    ERROR_MATCH(HttpStatus.BAD_REQUEST, 7005, "매칭에러", "com.sfull.forward.golf.user.error.match"),
    NO_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, 7006, "패스워드가 맞지 않습니다.", "com.sfull.forward.golf.user.not.match"),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST,7007, "잘못된 인증 토큰 입니다.", "exception.invalid.access.token"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,7008, "잘못된 Refresh 토큰 입니다.", "exception.invalid.refresh.token"),
    NOT_FOUND_ACCESS_TOKEN(HttpStatus.NOT_FOUND,7009, "토큰 정보를 찾을 수 없습니다.", "exception.failed.not.found.access.token"),
    DO_NOT_SUPPORT_AUTH_METHOD(HttpStatus.BAD_REQUEST,7010, "지원하지 않는 인증 요청 입니다.", "exception.do.not.support.auth.method"),
    DO_NOT_SUPPORT_AUTH_METHOD_OR_INVALID_OAUTH_TOKEN(HttpStatus.BAD_REQUEST,7011, "지원하지 않는 인증 이거나 잘못된 토큰 입니다.", "exception.do.not.support.auth.method"),
    NOT_FOUND_SOCIAL_AUTH_INFO(HttpStatus.NOT_FOUND, 7012, "소셜에 연결된 인증 정보를 찾을 수 없습니다.", "exception.not.found.social.auth.info"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST,7013, "잘못된 패스워드 입니다.", "exception.wrong.password"),
    EXISTS_SOCIAL_ACCOUNT_EXCEPTION(HttpStatus.BAD_REQUEST,7014, "이미 등록된 소셜 계정입니다.", "exception.exists.social.account"),
    EXISTS_ACCOUNT_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST,7015, "이미 등록된 인증 타입입니다.", "exception.exists.social.type"),
    DUPLICATE_LOCAL_ACCOUNT_EXCEPTION(HttpStatus.BAD_REQUEST,7016, "이미 등록된 계정 정보입니다.", "exception.duplicate.local.account"),
    NOT_MATCH_ID_EXCEPTION(HttpStatus.BAD_REQUEST,7017, "접근할 수 없는 계정입니다.", "exception.not.match.account"),
    MISSING_REQUIRED_PARAMETERS(HttpStatus.BAD_REQUEST,10000, "필수 요청 정보가 없습니다.", "exception.missing.required.parameters"),
    PARSING_ERROR_ORDER(HttpStatus.BAD_REQUEST,11000, "주문정보를 파싱하는 중 오류가 발생했습니다.", "com.sfull.forward.golf.order.error.parsing"),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND,11001, "주문정보를 찾을 수 없습니다.", "com.sfull.forward.golf.order.not.found"),
    DUPLICATE_ORDER(HttpStatus.BAD_REQUEST,11002, "이미 결제된 정보가 전달되었습니다.", "com.sfull.forward.golf.order.duplication"),
    NOT_MATCH_ORDER(HttpStatus.BAD_REQUEST,11003, "결제 정보와 주문정보가 일치하지 않습니다.", "com.sfull.forward.golf.order.not.match"),
    NOT_REQUEST_ORDER(HttpStatus.BAD_REQUEST,11004, "참가자수를 초과하였습니다.", "com.sfull.forward.golf.order.not.order"),
    PORTONE_ERROR_ORDER(HttpStatus.BAD_REQUEST,12000, "포트원에서 정보를 가져오는 중 에러가 발생했습니다.", "com.sfull.forward.golf.portone.error"),
    PORTONE_ERROR_TOKEN(HttpStatus.BAD_REQUEST,12001, "포트원에서 토큰을 가져오는 중 에러가 발생했습니다.", "com.sfull.forward.golf.portone.error.token"),
    PORTONE_ERROR_BILL(HttpStatus.BAD_REQUEST,12002, "포트원에서 결제정보를 가져오는 중 에러가 발생했습니다.", "com.sfull.forward.golf.portone.error.bill"),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND,13000, "결제 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.payment.not.found"),
    EXISTS_REVIEW(HttpStatus.BAD_REQUEST,14000, "이미 리뷰가 작성된 결제내역입니다.", "com.sfull.forward.golf.review.exists"),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,14001, "찾을 수 없는 리뷰입니다.", "com.sfull.forward.golf.review.not.found"),
    NOT_FOUND_GOLFER(HttpStatus.NOT_FOUND,15000, "골퍼 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.golfer.not.found"),
    NOT_MATCH_GOLFER(HttpStatus.BAD_REQUEST,15001, "해당 제품에 등록되지 않은 골퍼 정보입니다.", "com.sfull.forward.golf.golfer.not.match"),
    EXISTS_REFUND(HttpStatus.BAD_REQUEST,16000, "이미 환불요청이된 결제내역입니다.", "com.sfull.forward.golf.refund.exists"),
    NOT_FOUND_REFUND(HttpStatus.NOT_FOUND,16001, "환불 요청 내역을 찾을 수 없습니다.", "com.sfull.forward.golf.refund.not.found"),
    NOT_FOUND_ADMIN_USER(HttpStatus.NOT_FOUND,17000, "관리자 계정을 찾을 수 없습니다.", "com.sfull.forward.golf.admin.not.found"),
    USER_ERROR_ESTIMATE(HttpStatus.BAD_REQUEST,18000, "견적을 작성할 수 없는 유저 입니다.", "com.sfull.forward.golf.estimate.error.user"),
    NOT_FOUND_ESTIMATE(HttpStatus.NOT_FOUND,18001, "견적 요청 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.estimate.not.found"),
    NOT_FOUND_CLUB(HttpStatus.NOT_FOUND,19000, "골프장 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.club.not.found"),
    NOT_FOUND_ROUND(HttpStatus.NOT_FOUND,20000, "라운드 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.round.not.found"),
    NOT_FOUND_COURSE(HttpStatus.NOT_FOUND,21000, "코스 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.course.not.found"),
    NOT_FOUND_SCORE(HttpStatus.NOT_FOUND,22000, "스코어 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.score.not.found"),
    NOT_FOUND_GOLF_SWING_CLUB(HttpStatus.NOT_FOUND,23000, "클럽 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.swing.club.not.found"),
    NOT_FOUND_GOLF_TEE(HttpStatus.NOT_FOUND,24000, "티 정보를 찾을 수 없습니다.", "com.sfull.forward.golf.tee.not.found"),
    NOT_FOUND_WEATHER(HttpStatus.NOT_FOUND,25000, "날씨 정보를 찾을 수 없습니다.", "com.sfull.forward.weather.not.found"),
    NOT_FOUND_STORE_ID(HttpStatus.NOT_FOUND, 26000, "매장 정보를 찾을 수 없습니다.", "com.sfull.forward.weather.not.found"),
    NOT_FOUND_DATE(HttpStatus.NOT_FOUND, 27000, "시작 및 마지막 날짜의 정보가 없습니다.", "com.sfull.forward.weather.not.found"),
    NOT_VALID_ORDER(HttpStatus.NOT_FOUND, 28000, "재고보다 많은 양을 주문할 수 없습니다.", "com.sfull.forward.weather.not.found"),
    NOT_VALID_PRODUCT_STATE(HttpStatus.NOT_FOUND, 29000, "주문할 수 없는 상태의 제품입니다.", "com.sfull.forward.weather.not.found"),
    NOT_VALID_PRODUCT_STATE_UPDATE(HttpStatus.NOT_FOUND, 30000, "제품의 상태를 변경할 수 없습니다.", "com.sfull.forward.weather.not.found"),
    NOT_VALID_ORDER_STATE_UPDATE(HttpStatus.NOT_FOUND, 31000, "결제 상태를 변경할 수 없습니다.", "com.sfull.forward.weather.not.found"),
    ;

    private HttpStatus status;
    private int errorCode;
    private String errorMsg;
    private String translate;

    BusinessLogicExceptionDefinedReason(HttpStatus status, int errorCode, String errorMsg, String translate) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.translate = translate;
        this.status = status;
    }
}
