package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.LpnInfoModel;

public class LpnInfoResponseData {
    @SerializedName("lpnInfo")
    LpnInfoModel lpnInfoModel;

    @SerializedName("notFound")
    boolean notFound;

    public boolean isNotFound() {
        return notFound;
    }

    public LpnInfoModel getLpnInfoModel() {
        return lpnInfoModel;
    }
}
