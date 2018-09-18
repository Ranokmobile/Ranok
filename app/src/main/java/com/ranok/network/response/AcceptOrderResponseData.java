package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class AcceptOrderResponseData {
    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("qtyRecieved")
    public int qtyRecieved;

    @SerializedName("resultMessage")
    public String resultMessage;

    @SerializedName("lpnRejected")
    public String lpnRejected;

    public AcceptOrderResponseData(int resultCode, int qtyRecieved, String resultMessage, String lpnRejected) {
        this.resultCode = resultCode;
        this.qtyRecieved = qtyRecieved;
        this.resultMessage = resultMessage;
        this.lpnRejected = lpnRejected;
    }



}
