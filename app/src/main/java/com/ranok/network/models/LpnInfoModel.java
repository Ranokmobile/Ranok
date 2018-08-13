package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@Entity
public class LpnInfoModel {

    @SerializedName("lpn")
    @ColumnInfo(name="lpn")
    String lpn;

    @SerializedName("parentLpn")
    @ColumnInfo(name="parentLpn")
    String parentLpn;

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

    @SerializedName("weight")
    @ColumnInfo(name="weight")
    Double weight;

    @SerializedName("volume")
    @ColumnInfo(name="volume")
    Double volume;

    public String getCreation() {
        return createdBy + " (" + creationDate + ")";
    }

    public String getEdition() {
        return lastUpdatedBy + " (" + lastUpdatedDate + ")";
    }

    public String getCharacteristics() {
       // return weight + " кг," + volume + " м3";
        String res = trimTrailingZeros(String.format(Locale.getDefault(),"%.12f", weight)) + " кг, ";
        res = res + trimTrailingZeros(String.format(Locale.getDefault(),"%.12f", volume)) + " м3";
        return res;
        //return String.format(Locale.getDefault(), "%.12f кг, %.12f м3", weight, volume);

    }

    private static String trimTrailingZeros(String number) {
        if(!number.contains(".") && !number.contains(",")) {
            return number;
        }

        return number.replaceAll("\\.?0*$", "");
    }


    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getParentLpn() {
        return parentLpn;
    }

    public String getParentLpnName() {
        return parentLpn == null ? "Отсутствует" : parentLpn;
    }


    public void setParentLpn(String parentLpn) {
        this.parentLpn = parentLpn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
/*
CREATE OR REPLACE TYPE t_lpn_info is OBJECT(
  lpn VARCHAR2(100),
  parentLpn VARCHAR2(100),
  createdBy  VARCHAR2(100),
  creationDate VARCHAR2(100),
  lastUpdatedBy VARCHAR2(100),
  lastUpdatedDate VARCHAR2(100),
  weight NUMBER,
  volume NUMBER
  )

*/