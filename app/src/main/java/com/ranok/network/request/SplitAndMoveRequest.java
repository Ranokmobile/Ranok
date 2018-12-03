package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class SplitAndMoveRequest {
    @SerializedName("split")
    public SplitLpnRequest splitLpnRequest;

    @SerializedName("move")
    public MoveLpnRequest moveLpnRequest;

    public SplitAndMoveRequest(SplitLpnRequest splitLpnRequest, MoveLpnRequest moveLpnRequest) {
        this.splitLpnRequest = splitLpnRequest;
        this.moveLpnRequest = moveLpnRequest;
    }
}
