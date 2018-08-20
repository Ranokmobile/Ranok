package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class SplitLpnRequest {
    @SerializedName("source_lpn")
    public String sourceLpn;

    @SerializedName("lot_number")
    public String lot;

    @SerializedName("item_code")
    public int itemCode;

    @SerializedName("quantity")
    public int qty;

    @SerializedName("address")
    public String address;

    public SplitLpnRequest(String sourceLpn, String lot, int itemCode, int qty, String address) {
        this.sourceLpn = sourceLpn;
        this.lot = lot;
        this.itemCode = itemCode;
        this.qty = qty;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
