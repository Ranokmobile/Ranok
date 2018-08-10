package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class PositionPlacesModel  { //t_place
    @SerializedName("lot")
    @ColumnInfo(name="lot")
    String lot ;

    @SerializedName("subinventoryCode")
    @ColumnInfo(name="subinventory_code")
    String subinventoryCode;

    @SerializedName("address")
    @ColumnInfo(name="address")
    String address;

    @SerializedName("lpn")
    @ColumnInfo(name="lpn")
    String lpn;

    @SerializedName("sysQuantity")
    @ColumnInfo(name="sysQuantity")
    int sysQuantity;

    @SerializedName("availQuantity")
    @ColumnInfo(name="availQuantity")
    int availQuantity;

    public String getQty(){
        if (sysQuantity==0 && availQuantity==0) return "";
        return String.valueOf(sysQuantity) + " (" + String.valueOf(availQuantity) + ")";
    }

    public String getLot() {
        return lot;
    }

    public String getSubinventoryCode() {
        return subinventoryCode;
    }

    public String getAddress() {
        return address;
    }

    public String getLpn() {
        return lpn;
    }

    public int getSysQuantity() {
        return sysQuantity;
    }

    public int getAvailQuantity() {
        return availQuantity;
    }
}