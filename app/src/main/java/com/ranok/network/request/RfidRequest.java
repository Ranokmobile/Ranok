package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class RfidRequest extends BaseRequest {
    @SerializedName("label")
    public String label;

    public RfidRequest(String label) {
        this.label = label;
    }
}
