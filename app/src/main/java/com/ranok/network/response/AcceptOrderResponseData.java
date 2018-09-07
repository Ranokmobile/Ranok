package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class AcceptOrderResponseData {
    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("qtyRecieved")
    public int qtyRecieved;

    @SerializedName("resultMessage")
    public String resultMessage;

}
