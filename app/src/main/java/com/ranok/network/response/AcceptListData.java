package com.ranok.network.response;

import com.google.gson.annotations.SerializedName;
import com.ranok.network.models.AcceptListModel;

import java.util.List;

public class AcceptListData {
    @SerializedName("acceptList")
    List<AcceptListModel> acceptList;

    public List<AcceptListModel> getAcceptList() {
        return acceptList;
    }

    public void setAcceptList(List<AcceptListModel> acceptList) {
        this.acceptList = acceptList;
    }
}
