package com.ranok.network.response;

import com.ranok.network.models.PositionLotAttributesModel;

import java.util.List;

public class LotInfoData {
    List<PositionLotAttributesModel> lots;

    public List<PositionLotAttributesModel> getLots() {
        return lots;
    }

    public void setLots(List<PositionLotAttributesModel> lots) {
        this.lots = lots;
    }
}
