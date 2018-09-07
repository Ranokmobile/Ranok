package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class AcceptOrderRequest {
    @SerializedName("lpn")
    String lpn;

    @SerializedName("itemCode")
    String itemCode;

    @SerializedName("lot")
    String lot;

    @SerializedName("qty")
    int qty;

    @SerializedName("qualityCode")
    String qualityCode;

    public AcceptOrderRequest(String lpn, String itemCode, String lot, int qty, String qualityCode) {
        this.lpn = lpn;
        this.itemCode = itemCode;
        this.lot = lot;
        this.qty = qty;
        this.qualityCode = qualityCode;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getQualityCode() {
        return qualityCode;
    }

    public void setQualityCode(String qualityCode) {
        this.qualityCode = qualityCode;
    }
}
