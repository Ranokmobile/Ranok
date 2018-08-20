package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranok.ui.dialogs.SelectDialogFragment;

public class LpnPositionModel implements Parcelable, SelectDialogFragment.Selectable {
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

    public String getISOAddress(){
        return address.substring(3);
    }

    @Override
    public String getName() {
        return getCode() + " " + getAddress() + getLot();
    }

    @Override
    public int getId() {
        return Integer.parseInt(code);
    }

    @Override
    public int getType() {
        return 0;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.address);
        dest.writeInt(this.inventoryItemId);
        dest.writeString(this.lot);
        dest.writeInt(this.sysQty);
        dest.writeInt(this.availQty);
    }

    public LpnPositionModel() {
    }

    protected LpnPositionModel(Parcel in) {
        this.code = in.readString();
        this.address = in.readString();
        this.inventoryItemId = in.readInt();
        this.lot = in.readString();
        this.sysQty = in.readInt();
        this.availQty = in.readInt();
    }

    public static final Parcelable.Creator<LpnPositionModel> CREATOR = new Parcelable.Creator<LpnPositionModel>() {
        @Override
        public LpnPositionModel createFromParcel(Parcel source) {
            return new LpnPositionModel(source);
        }

        @Override
        public LpnPositionModel[] newArray(int size) {
            return new LpnPositionModel[size];
        }
    };
}
