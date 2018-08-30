package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class AcceptListRequest {
    @SerializedName("lpn")
    public String lpn;

    public AcceptListRequest(String lpn) {
        this.lpn = lpn;
    }
}
