package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class PlaceRequest {
    @SerializedName("rack")
    public String rack;

    @SerializedName("space")
    public String space;

    @SerializedName("floor")
    public String floor;

    @SerializedName("block")
    public String block;

    public PlaceRequest(String rack, String space, String floor, String block) {
        this.rack = rack;
        this.space = space;
        this.floor = floor;
        this.block = block;
    }
}
//p_rack VARCHAR2, p_space VARCHAR2, p_floor VARCHAR2, p_block VARCHAR2
