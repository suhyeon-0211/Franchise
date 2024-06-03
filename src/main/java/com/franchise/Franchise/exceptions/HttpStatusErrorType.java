package com.franchise.Franchise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.franchise.Franchise.response.EmptyResponse;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.ResponseMeta;

import lombok.Getter;

@Getter
public enum HttpStatusErrorType implements BusinessLogicExceptionReason{
    NOT_FOUND_AUTH(402, "인증 정보를 찾을 수 없습니다.", "exception.not.found.auth", HttpStatus.FORBIDDEN),
    ACCESS_DENIED(403, "권한이 없습니다.", "exception.access.denied.error", HttpStatus.FORBIDDEN),
    EXPIRED_TOKEN(440, "만료된 토큰입니다.", "exception.expired.token", HttpStatus.FORBIDDEN),
    WRONG_TOKEN(441, "잘못된 토큰입니다.", "exception.wrong.token", HttpStatus.FORBIDDEN),

    UNKNOWN_SERVER_ERROR(1000, "서버 오류 입니다. 문제가 계속 발생할 경우 고객센터에 문의해주세요.", "exception.unknown.server.error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_RESOURCE(1004, "자료를 찾을 수 없습니다.", "exception.resource.not.found", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER(1005, "사용자 정보를 찾을 수 없습니다.", "exception.user.not.found", HttpStatus.NOT_FOUND),
    NOT_FOUND_AREA_CODE(1006, "지역코드를 찾을 수 없습니다.", "exception.area.code.not.found", HttpStatus.NOT_FOUND),
    NOT_FOUND_EXISTS_DATA(1007, "이미 등록된 정보가 있습니다.", "exception.not.found.exists.data", HttpStatus.NOT_FOUND),
    NOT_FOUND_PRODUCT(1010, "상품을 찾을 수 없습니다.", "exception.product.not.found", HttpStatus.NOT_FOUND),

    NOT_FOUND_FRIEND(1030, "찾을수 없는 아이디입니다.", "exception.friend.not.found", HttpStatus.NOT_FOUND),

    NOT_FOUND_BOARD(1040, "자료를 찾을 수 없습니다.", "exception.board.not.found", HttpStatus.NOT_FOUND),
    NOT_FOUND_POST(1041, "자료를 찾을 수 없습니다.", "exception.post.not.found", HttpStatus.NOT_FOUND),

    INVALID_REQUEST(1100, "입력 정보가 잘못되었습니다. 입력한 정보를 확인해주세요.", "exception.invalid.request", HttpStatus.BAD_REQUEST),


    FAILED_SIGN_IN_USER_NOT_FOUND(1200, "사용자 정보가 없습니다. 회원 가입을 해주세요.", "exception.failed.signin.user.not.found", HttpStatus.BAD_REQUEST),
    FAILED_SIGN_IN_NO_MATCH_PASSWORD(1201, "아이디, 패스워드가 일치하지 않습니다.", "exception.failed.signin.no.match.password", HttpStatus.BAD_REQUEST),
    FAILED_NOT_FOUND_ACCOUNT(1202, "해당 아이디로 가입 정보가 없습니다.", "exception.failed.not.found.account", HttpStatus.BAD_REQUEST),
    FAILED_NOT_ACTIVATION_ACCOUNT(1203, "활성화된 계정이 아닙니다. 문제가 계속 발생할 경우 고객센터에 문의해주세요.", "exception.failed.not.activation.account", HttpStatus.BAD_REQUEST),

    FAILED_NO_HAS_MANAGER(1210, "접근 권한이 없습니다. (관리자 권한 없음)", "exception.failed.no.has.manager", HttpStatus.BAD_REQUEST),


    INVALID_ACCESS_TOKEN(BusinessLogicExceptionDefinedReason.INVALID_ACCESS_TOKEN, HttpStatus.BAD_REQUEST),
    ;

    private HttpStatus status;
    private int errorCode;
    private String errorMsg;
    private String translate;

    HttpStatusErrorType(int errorCode, String errorMsg, String translate, HttpStatus status) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.translate = translate;
    }

    HttpStatusErrorType(BusinessLogicExceptionReason reason, HttpStatus status) {
        this.status = status;
        this.errorCode = reason.getErrorCode();
        this.errorMsg = reason.getErrorMsg();
        this.translate = reason.getTranslate();
    }

    public ResponseEntity<EmptyResponse> response() {
        return GenericResponse.of(getStatus(), new ResponseMeta(getErrorCode(), getErrorMsg(), getTranslate()));
    }
}
