package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class BarcodeRequest extends BaseRequest {
    @SerializedName("barcode")
    public String barcode;

    public BarcodeRequest(String barcode) {
        this.barcode = barcode;
    }
}
