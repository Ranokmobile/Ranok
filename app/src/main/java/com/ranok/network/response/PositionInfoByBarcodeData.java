package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.network.models.PosInReceiptModel;
import com.ranok.network.models.PositionInfoModel;
import com.ranok.network.models.PositionLotAttributesModel;

import java.util.ArrayList;
import java.util.List;

public class PositionInfoByBarcodeData {
    @SerializedName("positionList")
    ArrayList<PositionInfoModel> positionList = new ArrayList<>();

    @SerializedName("positionInfo")
    PositionInfoModel positionInfo;

    @SerializedName("positionInReceipt")
    List<PosInReceiptModel> positionInReceiptList;

    @SerializedName("positionPlaces")
    List<PlaceInfoModel> positionPlacesList = new ArrayList<>();

    @SerializedName("positionLotAttributes")
    List<PositionLotAttributesModel> positionLotAttributesList = new ArrayList<>();

    public ArrayList<PositionInfoModel> getPositionList() {
        return positionList;
    }

    public PositionInfoModel getPositionInfo() {
        return positionInfo;
    }

    public List<PlaceInfoModel> getPositionPlacesList() {
        return positionPlacesList;
    }

    public List<PositionLotAttributesModel> getPositionLotAttributesList() {
        return positionLotAttributesList;
    }

    public List<PosInReceiptModel> getPositionInReceiptList() {
        return positionInReceiptList;
    }
}
