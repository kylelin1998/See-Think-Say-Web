package web.common.response;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Response<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    private Response() {}

    private Response(RspCode rspCode) {
        this.code = rspCode.getCode();
        this.message = rspCode.getMessage();
    }

    private Response(RspCode rspCode, T data) {
        this.code = rspCode.getCode();
        this.message = rspCode.getMessage();
        this.data = data;
    }

    private Response(RspCode rspCode, String msg) {
        this.code = rspCode.getCode();
        this.message = msg;
    }

    private Response(RspCode rspCode, String msg, T data) {
        this.code = rspCode.getCode();
        this.message = msg;
        this.data = data;
    }

    public static Response success() {
        return new Response(RspCode.SUCCESS);
    }

    public static Response error() {
        return new Response(RspCode.ERROR);
    }

    public static Response build(RspCode rspCode) {
        return new Response(rspCode);
    }

    public static<T> Response<T> build(RspCode rspCode, T data) {
        return new Response(rspCode, data);
    }

    public static Response build(RspCode rspCode, String msg) {
        return new Response(rspCode, msg);
    }

    public static<T> Response<T> build(RspCode rspCode, String msg, T data) {
        return new Response(rspCode, msg, data);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
