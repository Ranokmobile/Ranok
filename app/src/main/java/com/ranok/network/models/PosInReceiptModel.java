package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.ranok.utils.StringUtils;

@Entity
public class PosInReceiptModel {
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
}
