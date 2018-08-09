package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class BarcodeRequest extends BaseRequest {
    @SerializedName("barcode")
    public String barcode;

    @SerializedName("barcode_type")
    public String barcodeType;

    public BarcodeRequest(String barcode) {
        this.barcode = barcode;
    }

    public BarcodeRequest(String barcode, String barcodeType) {
        this.barcode = barcode;
        this.barcodeType = barcodeType;
    }
}
