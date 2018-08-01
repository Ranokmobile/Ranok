package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("code")
    public Integer code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
