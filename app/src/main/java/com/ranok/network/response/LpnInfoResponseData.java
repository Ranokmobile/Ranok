package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.LpnInfoModel;
import com.ranok.network.models.LpnPositionModel;

import java.util.ArrayList;

public class LpnInfoResponseData {
    @SerializedName("lpnInfo")
    LpnInfoModel lpnInfoModel;

    @SerializedName("listLpnPositions")
    ArrayList<LpnPositionModel> listLpnPositions;

    @SerializedName("notFound")
    boolean notFound;

    public ArrayList<LpnPositionModel> getListLpnPositions() {
        return listLpnPositions;
    }

    public boolean isNotFound() {
        return notFound;
    }

    public LpnInfoModel getLpnInfoModel() {
        return lpnInfoModel;
    }
}
