package com.ranok.ui.info_position.lot_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.models.PositionLotAttributesModel;
import com.ranok.network.response.PositionInfoByBarcodeData;
import com.ranok.rx_bus.RxPosInfo;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;


public class LotInfoVM extends BaseViewModel<LotInfoIView> {

    private PositionInfoByBarcodeData data = new PositionInfoByBarcodeData();

    private RecyclerBindingAdapter<PositionLotAttributesModel> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_lot_attributes, BR.viewModel, data.getPositionLotAttributesList());


    public RecyclerBindingAdapter<PositionLotAttributesModel> getAdapter() {
        return adapter;
    }

    public PositionInfoByBarcodeData getData() {
        return data;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        compositeDisposable.add(RxPosInfo.getInstance().getEvents().subscribe(this::gotNewData));
    }

    public boolean isPositionDataCorrect(){
        if (data.getPositionLotAttributesList() == null) return false;
        return true;
    }

    private void gotNewData(PositionInfoByBarcodeData positionInfoByBarcodeData) {
        data = positionInfoByBarcodeData;
//        notifyChange();
        if (data.getPositionLotAttributesList() == null) {
            adapter.setItems( new ArrayList<>());
        } else {
            adapter.setItems(data.getPositionLotAttributesList());
        }
    }

}
