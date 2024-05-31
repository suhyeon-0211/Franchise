package com.franchise.Franchise.response;


//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.function.Supplier;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Schema(description = "Generic Response")
public class GenericResponse<T> {
    private ResponseMeta meta;
    private T data;

    public static ResponseEntity<EmptyResponse> of(final HttpStatus status, final ResponseMeta meta) {
        return of(status, meta, null, EmptyResponse::new);
    }

    public static <T> ResponseEntity<T> of(final HttpStatus status, final T data) {
        return ResponseEntity.status(status).body(data);
    }

    public static <T> ResponseEntity<GenericResponse<T>> of(final HttpStatus status,
                                                            final ResponseMeta meta,
                                                            final T data) {
        return ResponseEntity.status(status).body(new GenericResponse<>(meta, data));
    }

    public static <T, R extends GenericResponse<T>> ResponseEntity<R> of(final HttpStatus status,
                                                                         final ResponseMeta meta,
                                                                         final T data,
                                                                         final Supplier<R> constructor) {
        if (constructor == null) {
            /* 정상동작 확인 소스 => 추후 변경해야하는지 확인 필요
            R resp = ((R)new GenericResponse<T>(ResponseMeta.EMPTY, null));
            return ResponseEntity.status(status).body(resp);
            */
            return ResponseEntity.status(status).body(null);
        }

        final R response = constructor.get();
        response.setMeta(meta);
        response.setData(data);

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<GenericResponse<T>> created(URI uri, final T data) {
        final GenericResponse<T> res = new GenericResponse<>(ResponseMeta.EMPTY, data);

        return ResponseEntity.created(uri).body(res);
    }

    public static <T, R extends GenericResponse<T>> ResponseEntity<R> ok() {
        return ok(ResponseMeta.EMPTY, null, null);
    }

    public static <T> ResponseEntity<GenericResponse<T>> ok(final T data) {
        return of(HttpStatus.OK, ResponseMeta.EMPTY, data);
    }

    public static <T, R extends GenericResponse<T>> ResponseEntity<R> ok(final ResponseMeta meta, final T data, final Supplier<R> constructor) {
        return of(HttpStatus.OK, meta, data, constructor);
    }

    public static <T, R extends GenericResponse<T>> ResponseEntity<R> ok(final T data, final Supplier<R> constructor) {
        return ok(ResponseMeta.EMPTY, data, constructor);
    }

}
