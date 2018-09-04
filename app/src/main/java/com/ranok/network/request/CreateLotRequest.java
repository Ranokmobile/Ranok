package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class CreateLotRequest {
    @SerializedName("itemCode")
    int itemCode;

    @SerializedName("lotNumber")
    String lotNumber;

    @SerializedName("posLength")
    String posLength;

    @SerializedName("posWidth")
    String posWidth;

    @SerializedName("posHeight")
    String posHeight;

    @SerializedName("posWeight")
    String posWeight;

    @SerializedName("posHardness")
    String posHardness;

    @SerializedName("packLength")
    String packLength;

    @SerializedName("packWidth")
    String packWidth;

    @SerializedName("packHeight")
    String packHeight;

    @SerializedName("packWeight")
    String packWeight;

    @SerializedName("packType")
    String packType;

    @SerializedName("packHardness")
    String packHardness;

    @SerializedName("packStandart")
    String packStandart;

    public CreateLotRequest(int itemCode, String lotNumber, String posLength, String posWidth, String posHeight, String posWeight, String posHardness, String packLength, String packWidth, String packHeight, String packWeight, String packType, String packHardness, String packStandart) {
        this.itemCode = itemCode;
        this.lotNumber = lotNumber;
        this.posLength = posLength;
        this.posWidth = posWidth;
        this.posHeight = posHeight;
        this.posWeight = posWeight;
        this.posHardness = posHardness;
        this.packLength = packLength;
        this.packWidth = packWidth;
        this.packHeight = packHeight;
        this.packWeight = packWeight;
        this.packType = packType;
        this.packHardness = packHardness;
        this.packStandart = packStandart;
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
}