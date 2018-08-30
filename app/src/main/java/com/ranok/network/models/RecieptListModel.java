package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class RecieptListModel {
    @SerializedName("lineNumber")
    @ColumnInfo(name="lineNumber")
    String lineNumber;

    @SerializedName("itemId")
    @ColumnInfo(name="itemId")
    int itemId;

    @SerializedName("itemCode")
    @ColumnInfo(name="itemCode")
    String itemCode;

    @SerializedName("prodCodeOld")
    @ColumnInfo(name="prodCodeOld")
    String prodCodeOld;

    @SerializedName("prodCode")
    @ColumnInfo(name="prodCode")
    String prodCode;

    @SerializedName("itemName")
    @ColumnInfo(name="itemName")
    String itemName;

    @SerializedName("packStandart")
    @ColumnInfo(name="packStandart")
    String packStandart;

    @SerializedName("totalQuantity")
    @ColumnInfo(name="totalQuantity")
    int totalQuantity;

    @SerializedName("availQuantity")
    @ColumnInfo(name="availQuantity")
    int availQuantity;

    @SerializedName("lots")
    @ColumnInfo(name="lots")
    String lots;

    public String getName(){
        return lineNumber + ") " + itemCode + " - " + prodCode + "; " + itemName
                + "[" + String.valueOf(availQuantity) + "/" +   String.valueOf(totalQuantity)+ "]";
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProdCodeOld() {
        return prodCodeOld;
    }

    public void setProdCodeOld(String prodCodeOld) {
        this.prodCodeOld = prodCodeOld;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPackStandart() {
        return packStandart;
    }

    public void setPackStandart(String packStandart) {
        this.packStandart = packStandart;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getAvailQuantity() {
        return availQuantity;
    }

    public void setAvailQuantity(int availQuantity) {
        this.availQuantity = availQuantity;
    }

    public String getLots() {
        return lots;
    }

    public void setLots(String lots) {
        this.lots = lots;
    }
}
