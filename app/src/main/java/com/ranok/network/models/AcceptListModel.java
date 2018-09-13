package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AcceptListModel implements Parcelable {

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


    public String getFrom(){
        return " из " + String.valueOf(quantity);
    }

    public String getItemSpec(){
        return itemCode + " / " + itemName;
    }

    public int getMaxQtySymbols(){
        return String.valueOf(quantity).length();
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemCode);
        dest.writeString(this.itemName);
        dest.writeInt(this.itemId);
        dest.writeInt(this.quantity);
        dest.writeString(this.lot);
        dest.writeString(this.lpn);
    }

    public AcceptListModel() {
    }

    protected AcceptListModel(Parcel in) {
        this.itemCode = in.readString();
        this.itemName = in.readString();
        this.itemId = in.readInt();
        this.quantity = in.readInt();
        this.lot = in.readString();
        this.lpn = in.readString();
    }

    public static final Parcelable.Creator<AcceptListModel> CREATOR = new Parcelable.Creator<AcceptListModel>() {
        @Override
        public AcceptListModel createFromParcel(Parcel source) {
            return new AcceptListModel(source);
        }

        @Override
        public AcceptListModel[] newArray(int size) {
            return new AcceptListModel[size];
        }
    };
}
