package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class PositionLotAttributesModel {
    @SerializedName("lotNumber")
    @ColumnInfo(name="lot_number")
    String lotNumber;

    @SerializedName("posLength")
    @ColumnInfo(name="pos_length")
    String posLength;

    @SerializedName("posWidth")
    @ColumnInfo(name="pos_width")
    String posWidth;

    @SerializedName("posHeight")
    @ColumnInfo(name="pos_height")
    String posHeight;

    @SerializedName("posWeight")
    @ColumnInfo(name="pos_weight")
    String posWeight;

    @SerializedName("posHardness")
    @ColumnInfo(name="pos_hardness")
    String posHardness;

    @SerializedName("packLength")
    @ColumnInfo(name="pack_length")
    String packLength;

    @SerializedName("packWidth")
    @ColumnInfo(name="pack_width")
    String packWidth;

    @SerializedName("packHeight")
    @ColumnInfo(name="pack_height")
    String packHeight;

    @SerializedName("packWeight")
    @ColumnInfo(name="pack_weight")
    String packWeight;

    @SerializedName("packType")
    @ColumnInfo(name="pack_type")
    String packType;

    @SerializedName("packHardness")
    @ColumnInfo(name="pack_hardness")
    String packHardness;

    @SerializedName("packStandart")
    @ColumnInfo(name="pack_standart")
    String packStandart;

    @SerializedName("createdBy")
    @ColumnInfo(name="createdBy")
    String createdBy;

    @SerializedName("creationDate")
    @ColumnInfo(name="creationDate")
    String creationDate;

    @SerializedName("lastUpdatedBy")
    @ColumnInfo(name="lastUpdatedBy")
    String lastUpdatedBy;

    @SerializedName("lastUpdatedDate")
    @ColumnInfo(name="lastUpdatedDate")
    String lastUpdatedDate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public String getPosLength() {
        return posLength;
    }

    public String getPosWidth() {
        return posWidth;
    }

    public String getPosHeight() {
        return posHeight;
    }

    public String getPosWeight() {
        return posWeight;
    }

    public String getPosHardness() {
        return posHardness;
    }

    public String getPackLength() {
        return packLength;
    }

    public String getPackWidth() {
        return packWidth;
    }

    public String getPackHeight() {
        return packHeight;
    }

    public String getPackWeight() {
        return packWeight;
    }

    public String getPackType() {
        return packType;
    }

    public String getPackHardness() {
        return packHardness;
    }

    public String getPackStandart() {
        return packStandart;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }
}