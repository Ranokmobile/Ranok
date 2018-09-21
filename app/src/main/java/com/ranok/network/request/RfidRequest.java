package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class RfidRequest extends BaseRequest {
    @SerializedName("label")
    public String label;

    @SerializedName("instance")
    public String instance;

    public RfidRequest(String label, String instance) {
        this.label = label;
        this.instance = instance;
    }
}
