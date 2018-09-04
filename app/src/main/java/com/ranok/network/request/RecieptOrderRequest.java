package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class RecieptOrderRequest {
    @SerializedName("orderNum")
    int orderNum;

    @SerializedName("lineNum")
    int lineNum;

    @SerializedName("lot")
    String lot;

    @SerializedName("qty")
    int qty;

    @SerializedName("lpn")
    String lpn;

    public RecieptOrderRequest(int orderNum, int lineNum, String lot, int qty, String lpn) {
        this.orderNum = orderNum;
        this.lineNum = lineNum;
        this.lot = lot;
        this.qty = qty;
        this.lpn = lpn;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }
}
