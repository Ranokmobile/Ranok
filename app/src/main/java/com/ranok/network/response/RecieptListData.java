package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.RecieptListModel;

import java.util.List;

public class RecieptListData {
    @SerializedName("recieptList")
    List<RecieptListModel> recieptList;

    public List<RecieptListModel> getRecieptList() {
        return recieptList;
    }

    public void setRecieptList(List<RecieptListModel> recieptList) {
        this.recieptList = recieptList;
    }
}
