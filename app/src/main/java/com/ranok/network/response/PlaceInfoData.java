package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.PlaceInfoModel;

import java.util.List;

public class PlaceInfoData {
    @SerializedName("placeInfoList")
    List<PlaceInfoModel> placeInfoList;

    public List<PlaceInfoModel> getPlaceInfoList() {
        return placeInfoList;
    }
}
