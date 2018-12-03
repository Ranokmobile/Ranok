package com.ranok.network.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SplitLpnRequest implements Parcelable {
    @SerializedName("source_lpn")
    public String sourceLpn;

    @SerializedName("lot_number")
    public String lot;

    @SerializedName("item_code")
    public int itemCode;

    @SerializedName("quantity")
    public int qty;

    @SerializedName("address")
    public String address;

    public SplitLpnRequest(String sourceLpn, String lot, int itemCode, int qty, String address) {
        this.sourceLpn = sourceLpn;
        this.lot = lot;
        this.itemCode = itemCode;
        this.qty = qty;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSourceLpn() {
        return sourceLpn;
    }

    public void setSourceLpn(String sourceLpn) {
        this.sourceLpn = sourceLpn;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sourceLpn);
        dest.writeString(this.lot);
        dest.writeInt(this.itemCode);
        dest.writeInt(this.qty);
        dest.writeString(this.address);
    }

    protected SplitLpnRequest(Parcel in) {
        this.sourceLpn = in.readString();
        this.lot = in.readString();
        this.itemCode = in.readInt();
        this.qty = in.readInt();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<SplitLpnRequest> CREATOR = new Parcelable.Creator<SplitLpnRequest>() {
        @Override
        public SplitLpnRequest createFromParcel(Parcel source) {
            return new SplitLpnRequest(source);
        }

        @Override
        public SplitLpnRequest[] newArray(int size) {
            return new SplitLpnRequest[size];
        }
    };
}
