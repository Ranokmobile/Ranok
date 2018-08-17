package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class MoveLpnRequest {
    @SerializedName("source_lpn")
    public String sourceLpn;

    @SerializedName("target_lpn")
    public String targetLpn;

    @SerializedName("target_place_address")
    public String targetPlaceAddress;

    @SerializedName("move_type")
    public String moveType;   // 'full_lpn', 'lpn_content'

    public MoveLpnRequest(String sourceLpn, String targetLpn, String targetPlaceAddress, String moveType) {
        this.sourceLpn = sourceLpn;
        this.targetLpn = targetLpn;
        this.targetPlaceAddress = targetPlaceAddress;
        this.moveType = moveType;
    }
}
