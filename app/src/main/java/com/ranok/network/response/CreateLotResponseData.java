package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;

public class CreateLotResponseData {
    @SerializedName("resultCode")
    public int resultCode;

    @SerializedName("newLot")
    public String newLotCode;

    @SerializedName("resultMessage")
    public String resultMessage;

    public CreateLotResponseData(int resultCode, String newLotCode, String resultMessage) {
        this.resultCode = resultCode;
        this.newLotCode = newLotCode;
        this.resultMessage = resultMessage;
    }
}
