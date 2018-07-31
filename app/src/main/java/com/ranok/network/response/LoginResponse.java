package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse{
    @SerializedName("token")
    public String token;
}
