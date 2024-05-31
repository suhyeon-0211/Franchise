package com.franchise.Franchise.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMeta {
    public static final ResponseMeta EMPTY = new ResponseMeta(0, "", "", Collections.emptyMap());

    private int errCode;

    private String errMsg;
    private String errTranslate;

    private Map<String, Object> data;

    public ResponseMeta(final int errCode, final String errMsg) {
        this(errCode, errMsg, "", Collections.emptyMap());
    }

    public ResponseMeta(final int errCode, final String errMsg, final String errTranslate) {
        this(errCode, errMsg, errTranslate, Collections.emptyMap());
    }
}
