package com.ranok.network.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PackToLpnRequest implements Parcelable {
    @SerializedName("lot_number")
    public String lot;

    @SerializedName("item_code")
    public int itemCode;

    @SerializedName("quantity")
    public int qty;

    @SerializedName("address")
    public String address;

    public PackToLpnRequest(String lot, int itemCode, int qty, String address) {
        this.lot = lot;
        this.itemCode = itemCode;
        this.qty = qty;
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lot);
        dest.writeInt(this.itemCode);
        dest.writeInt(this.qty);
        dest.writeString(this.address);
    }

    protected PackToLpnRequest(Parcel in) {
        this.lot = in.readString();
        this.itemCode = in.readInt();
        this.qty = in.readInt();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<PackToLpnRequest> CREATOR = new Parcelable.Creator<PackToLpnRequest>() {
        @Override
        public PackToLpnRequest createFromParcel(Parcel source) {
            return new PackToLpnRequest(source);
        }

        @Override
        public PackToLpnRequest[] newArray(int size) {
            return new PackToLpnRequest[size];
        }
    };
}
