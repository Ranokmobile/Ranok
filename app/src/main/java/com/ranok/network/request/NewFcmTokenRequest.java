package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class NewFcmTokenRequest {
    @SerializedName("token")
    public String token;

    public NewFcmTokenRequest(String token) {
        this.token = token;
    }
}
