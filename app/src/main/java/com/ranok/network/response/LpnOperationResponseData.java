package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class LpnOperationResponseData {
    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("resultMessage")
    public String resultMessage;

    @SerializedName("newLpnCode")
    public String newLpnCode;

    public LpnOperationResponseData(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }


}
