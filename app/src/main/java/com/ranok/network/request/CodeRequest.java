package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class CodeRequest {
    @SerializedName("code")
    public String code;

    public CodeRequest(String code) {
        this.code = code;
    }
}
