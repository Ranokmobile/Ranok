package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class RecieptListRequest  {
    @SerializedName("order")
    public String order;

    public RecieptListRequest(String order) {
        this.order = order;
    }
}
