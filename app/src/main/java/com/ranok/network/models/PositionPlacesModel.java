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

}