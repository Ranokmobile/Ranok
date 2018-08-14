package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@Entity
public class PlaceInfoModel {
    @SerializedName("lot")
    @ColumnInfo(name="lot")
    String lot;

    @SerializedName("zone")
    @ColumnInfo(name="ZONE")
    String zone;

    @SerializedName("itemCode")
    @ColumnInfo(name="itemCode")
    String itemCode;

    @SerializedName("lpn")
    @ColumnInfo(name="lpn")
    String lpn;

    @SerializedName("sysQuantity")
    @ColumnInfo(name="sysQty")
    int sysQuantity;

    @SerializedName("address")
    @ColumnInfo(name="address")
    String address;

    @SerializedName("availQuantity")
    @ColumnInfo(name="availQty")
    int availQuantity;

    public String getLot() {
        return lot;
    }

    public String getQty() {
        return String.format(Locale.getDefault(), "%1$d (%2$d)", sysQuantity, availQuantity);
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public int getSysQuantity() {
        return sysQuantity;
    }

    public void setSysQuantity(int sysQuantity) {
        this.sysQuantity = sysQuantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailQuantity() {
        return availQuantity;
    }

    public void setAvailQuantity(int availQuantity) {
        this.availQuantity = availQuantity;
    }
}