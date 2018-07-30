package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("code")
    public Integer code;
    @SerializedName("message")
    public String message;
}
