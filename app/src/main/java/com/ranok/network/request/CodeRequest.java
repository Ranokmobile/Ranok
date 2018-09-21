package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class CodeRequest {
    @SerializedName("code")
    public String code;

    @SerializedName("instance")
    public String instance;

    public CodeRequest(String code, String instance) {
        this.code = code;
        this.instance = instance;
    }
}
