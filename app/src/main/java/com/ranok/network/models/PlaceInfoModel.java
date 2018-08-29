package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranok.ui.dialogs.SelectDialogFragment;

import java.util.Locale;

@Entity
public class PlaceInfoModel implements SelectDialogFragment.Selectable {
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


    @Override
    public String getName() {
        return itemCode + " / " + address + " / " + lot;
    }

    @Override
    public int getId() {
        return Integer.parseInt(itemCode);
    }

    @Override
    public int getType() {
        return 0;
    }

    public String getFullAddress(){
        return zone + ":" + address;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lot);
        dest.writeString(this.zone);
        dest.writeString(this.itemCode);
        dest.writeString(this.lpn);
        dest.writeInt(this.sysQuantity);
        dest.writeString(this.address);
        dest.writeInt(this.availQuantity);
    }

    public PlaceInfoModel() {
    }

    protected PlaceInfoModel(Parcel in) {
        this.lot = in.readString();
        this.zone = in.readString();
        this.itemCode = in.readString();
        this.lpn = in.readString();
        this.sysQuantity = in.readInt();
        this.address = in.readString();
        this.availQuantity = in.readInt();
    }

    public static final Parcelable.Creator<PlaceInfoModel> CREATOR = new Parcelable.Creator<PlaceInfoModel>() {
        @Override
        public PlaceInfoModel createFromParcel(Parcel source) {
            return new PlaceInfoModel(source);
        }

        @Override
        public PlaceInfoModel[] newArray(int size) {
            return new PlaceInfoModel[size];
        }
    };
}