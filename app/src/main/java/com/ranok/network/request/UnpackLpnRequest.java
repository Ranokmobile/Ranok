package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class UnpackLpnRequest {
    @SerializedName("source_lpn")
    public String sourceLpn;

    @SerializedName("lot_number")
    public String lot;

    @SerializedName("item_code")
    public int itemCode;

    @SerializedName("quantity")
    public int qty;

    public UnpackLpnRequest(String sourceLpn, String lot, int itemCode, int qty) {
        this.sourceLpn = sourceLpn;
        this.lot = lot;
        this.itemCode = itemCode;
        this.qty = qty;
    }

    public String getSourceLpn() {
        return sourceLpn;
    }

    public void setSourceLpn(String sourceLpn) {
        this.sourceLpn = sourceLpn;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
