package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class PositionCodeRequest {
    @SerializedName("code")
    public String code;

    public PositionCodeRequest(String code) {
        this.code = code;
    }
}
