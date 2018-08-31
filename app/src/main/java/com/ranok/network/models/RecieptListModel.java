package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecieptListModel implements Parcelable {
    @SerializedName("lineNumber")
    @ColumnInfo(name="lineNumber")
    int lineNumber;

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

    @SerializedName("orderName")
    @ColumnInfo(name="orderName")
    String orderName;


    public int getMaxQtySymbols(){
        return String.valueOf(availQuantity).length();
    }

    public String getFrom(){
        return "из " + String.valueOf(availQuantity);
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getName(){
        return lineNumber + ") " + itemCode + " - " + prodCode + "; " + itemName
                + "[" + String.valueOf(availQuantity) + "/" +   String.valueOf(totalQuantity)+ "]";
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.lineNumber);
        dest.writeInt(this.itemId);
        dest.writeString(this.itemCode);
        dest.writeString(this.prodCodeOld);
        dest.writeString(this.prodCode);
        dest.writeString(this.itemName);
        dest.writeString(this.packStandart);
        dest.writeInt(this.totalQuantity);
        dest.writeInt(this.availQuantity);
        dest.writeString(this.lots);
        dest.writeString(this.orderName);
    }

    public RecieptListModel() {
    }

    protected RecieptListModel(Parcel in) {
        this.lineNumber = in.readInt();
        this.itemId = in.readInt();
        this.itemCode = in.readString();
        this.prodCodeOld = in.readString();
        this.prodCode = in.readString();
        this.itemName = in.readString();
        this.packStandart = in.readString();
        this.totalQuantity = in.readInt();
        this.availQuantity = in.readInt();
        this.lots = in.readString();
        this.orderName = in.readString();
    }

    public static final Parcelable.Creator<RecieptListModel> CREATOR = new Parcelable.Creator<RecieptListModel>() {
        @Override
        public RecieptListModel createFromParcel(Parcel source) {
            return new RecieptListModel(source);
        }

        @Override
        public RecieptListModel[] newArray(int size) {
            return new RecieptListModel[size];
        }
    };
}
