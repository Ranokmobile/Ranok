package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class PrintLpnRequest {
    @SerializedName("lpn")
    public String lpn;

    public PrintLpnRequest(String lpn) {
        this.lpn = lpn;
    }
}
