package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.PositionInfoModel;
import com.ranok.network.models.PositionLotAttributesModel;
import com.ranok.network.models.PositionPlacesModel;

import java.util.ArrayList;
import java.util.List;

public class PositionInfoByBarcodeData {
    @SerializedName("positionList")
    ArrayList<PositionInfoModel> positionList;

    @SerializedName("positionInfo")
    PositionInfoModel positionInfo;

    @SerializedName("positionPlaces")
    List<PositionPlacesModel> positionPlacesList;

    @SerializedName("positionLotAttributes")
    List<PositionLotAttributesModel> positionLotAttributesList;

    public ArrayList<PositionInfoModel> getPositionList() {
        return positionList;
    }

    public PositionInfoModel getPositionInfo() {
        return positionInfo;
    }

    public List<PositionPlacesModel> getPositionPlacesList() {
        return positionPlacesList;
    }

    public List<PositionLotAttributesModel> getPositionLotAttributesList() {
        return positionLotAttributesList;
    }
}
