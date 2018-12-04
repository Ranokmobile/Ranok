package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class PackAndMoveLpnRequest {
    @SerializedName("pack")
    public PackToLpnRequest packToLpnRequest;

    @SerializedName("move")
    public MoveLpnRequest moveLpnRequest;

    public PackAndMoveLpnRequest(PackToLpnRequest packToLpnRequest, MoveLpnRequest moveLpnRequest) {
        this.packToLpnRequest = packToLpnRequest;
        this.moveLpnRequest = moveLpnRequest;
    }
}
