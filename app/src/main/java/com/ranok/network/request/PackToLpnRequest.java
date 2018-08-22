package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class PackToLpnRequest {
    @SerializedName("lot_number")
    public String lot;

    @SerializedName("item_code")
    public int itemCode;

    @SerializedName("quantity")
    public int qty;

    @SerializedName("address")
    public String address;

    public PackToLpnRequest(String lot, int itemCode, int qty, String address) {
        this.lot = lot;
        this.itemCode = itemCode;
        this.qty = qty;
        this.address = address;
    }
}
