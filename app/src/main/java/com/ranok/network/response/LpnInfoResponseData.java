package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.LpnInfoModel;
import com.ranok.network.models.LpnInfoPositionsInReceipt;
import com.ranok.network.models.PlaceInfoModel;

import java.util.ArrayList;

public class LpnInfoResponseData {
    @SerializedName("lpnInfo")
    LpnInfoModel lpnInfoModel;

    @SerializedName("listLpnPositions")
    ArrayList<PlaceInfoModel> listLpnPositions;

    @SerializedName("notFound")
    boolean notFound;

    @SerializedName("listLpnPositionsReceipt")
    ArrayList<LpnInfoPositionsInReceipt> listLpnPositionsReceipt;

    public ArrayList<LpnInfoPositionsInReceipt> getListLpnPositionsReceipt() {
        return listLpnPositionsReceipt;
    }

    public ArrayList<PlaceInfoModel> getListLpnPositions() {
        return listLpnPositions;
    }

    public boolean isNotFound() {
        return notFound;
    }

    public LpnInfoModel getLpnInfoModel() {
        return lpnInfoModel;
    }
}
