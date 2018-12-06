package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranok.utils.StringUtils;

@Entity
public class PosInReceiptModel implements Parcelable {
    @SerializedName("createDate")
    @ColumnInfo(name="create_date")
    String createDate;

    @SerializedName("quantity")
    @ColumnInfo(name="quantity")
    int quantity;

    @SerializedName("transactionType")
    @ColumnInfo(name="transaction_type")
    String transactionType;

    @SerializedName("transactionTypeName")
    @ColumnInfo(name="transaction_type_name")
    String transactionTypeName;

    @SerializedName("inspectionQualityCode")
    @ColumnInfo(name="inspection_quality_code")
    String inspectionQualityCode;

    @SerializedName("lpn")
    @ColumnInfo(name="lpn")
    String lpn;

    public String getCreateDate() {
        return createDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getQuantityString() {
        return String.valueOf(quantity);
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public String getInspectionQualityCode() {
        return inspectionQualityCode;
    }

    public String getInspectionQualityCodeCaption() {
        return StringUtils.isEmpty(inspectionQualityCode) ? "" : "Качество:";
    }

    public boolean isQualityPresent(){
        return !StringUtils.isEmpty(inspectionQualityCode);
    }

    public String getLpn() {
        return lpn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeInt(this.quantity);
        dest.writeString(this.transactionType);
        dest.writeString(this.transactionTypeName);
        dest.writeString(this.inspectionQualityCode);
        dest.writeString(this.lpn);
    }

    public PosInReceiptModel() {
    }

    protected PosInReceiptModel(Parcel in) {
        this.createDate = in.readString();
        this.quantity = in.readInt();
        this.transactionType = in.readString();
        this.transactionTypeName = in.readString();
        this.inspectionQualityCode = in.readString();
        this.lpn = in.readString();
    }

    public static final Parcelable.Creator<PosInReceiptModel> CREATOR = new Parcelable.Creator<PosInReceiptModel>() {
        @Override
        public PosInReceiptModel createFromParcel(Parcel source) {
            return new PosInReceiptModel(source);
        }

        @Override
        public PosInReceiptModel[] newArray(int size) {
            return new PosInReceiptModel[size];
        }
    };
}
