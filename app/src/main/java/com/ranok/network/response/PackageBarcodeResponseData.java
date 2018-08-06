package com.ranok.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PackageBarcodeResponseData implements Parcelable {
    @SerializedName("error_code")
    public Integer code;
    @SerializedName("barcode")
    public String barcode;


    public Integer getCode() {
        return code;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getColor(){
        return code == 0 ? android.R.color.holo_red_dark : android.R.color.holo_green_dark;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.code);
        dest.writeString(this.barcode);
    }

    public PackageBarcodeResponseData() {
    }

    protected PackageBarcodeResponseData(Parcel in) {
        this.code = (Integer) in.readValue(Integer.class.getClassLoader());
        this.barcode = in.readString();
    }

    public static final Parcelable.Creator<PackageBarcodeResponseData> CREATOR = new Parcelable.Creator<PackageBarcodeResponseData>() {
        @Override
        public PackageBarcodeResponseData createFromParcel(Parcel source) {
            return new PackageBarcodeResponseData(source);
        }

        @Override
        public PackageBarcodeResponseData[] newArray(int size) {
            return new PackageBarcodeResponseData[size];
        }
    };
}
