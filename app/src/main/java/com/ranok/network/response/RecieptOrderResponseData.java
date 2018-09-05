package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class RecieptOrderResponseData {
    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("qtyRecieved")
    public int qtyRecieved;

    @SerializedName("resultMessage")
    public String resultMessage;

    public RecieptOrderResponseData(int resultCode, int qtyRecieved, String resultMessage) {
        this.resultCode = resultCode;
        this.qtyRecieved = qtyRecieved;
        this.resultMessage = resultMessage;
    }
}
