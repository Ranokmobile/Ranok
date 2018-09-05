package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class AcceptListModel {

    @SerializedName("itemCode")
    @ColumnInfo(name="itemCode")
    String itemCode;

    @SerializedName("itemName")
    @ColumnInfo(name="itemName")
    String itemName;

    @SerializedName("itemId")
    @ColumnInfo(name="itemId")
    int itemId;

    @SerializedName("quantity")
    @ColumnInfo(name="quantity")
    int quantity;

    @SerializedName("lot")
    @ColumnInfo(name="lot")
    String lot;

    @SerializedName("lpn")
    @ColumnInfo(name="lpn")
    String lpn;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
