package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class LpnPositionModel {
    @SerializedName("code")
    @ColumnInfo(name="CODE")
    String code;

    @SerializedName("address")
    @ColumnInfo(name="address")
    String address;

    @SerializedName("inventoryItemId")
    @ColumnInfo(name="ID")
    int inventoryItemId;

    @SerializedName("lot")
    @ColumnInfo(name="lot")
    String lot;

    @SerializedName("sysQty")
    @ColumnInfo(name="sysQty")
    int sysQty;

    @SerializedName("availQty")
    @ColumnInfo(name="availQty")
    int availQty;

    public String getAddress() {
        return address;
    }

    public String getQty(){
        if (sysQty==0 && availQty==0) return "";
        return String.valueOf(sysQty) + "(" + String.valueOf(availQty) + ")";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getInventoryItemId() {
        return inventoryItemId;
    }

    public String getLot() {
        return lot;
    }

    public int getSysQty() {
        return sysQty;
    }

    public int getAvailQty() {
        return availQty;
    }

}
