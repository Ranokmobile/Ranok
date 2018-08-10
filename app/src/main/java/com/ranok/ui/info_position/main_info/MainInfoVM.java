package com.ranok.ui.info_position.main_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.models.PositionPlacesModel;
import com.ranok.network.response.PositionInfoByBarcodeData;
import com.ranok.rx_bus.RxPosInfo;
import com.ranok.ui.base.BaseViewModel;


public class MainInfoVM extends BaseViewModel<MainInfoIView> {

    private PositionInfoByBarcodeData data = new PositionInfoByBarcodeData();


    private RecyclerBindingAdapter<PositionPlacesModel> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_position_place, BR.viewModel, data.getPositionPlacesList());


    public RecyclerBindingAdapter<PositionPlacesModel> getAdapter() {
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
        if (data.getPositionInfo() == null) return false;
        return true;
    }

    public boolean isPlacesPresent(){
        if (data.getPositionPlacesList() == null) return false;
        if (data.getPositionPlacesList().size() == 0) return false;
        return true;
    }

    private void gotNewData(PositionInfoByBarcodeData positionInfoByBarcodeData) {
        data = positionInfoByBarcodeData;
        notifyChange();
        adapter.setItems(data.getPositionPlacesList());
    }
}
