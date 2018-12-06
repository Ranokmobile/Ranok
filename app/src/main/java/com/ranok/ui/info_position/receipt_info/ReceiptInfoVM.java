package com.ranok.ui.info_position.receipt_info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.RecyclerBindingAdapter;
import com.ranok.network.models.PosInReceiptModel;
import com.ranok.network.response.PositionInfoByBarcodeData;
import com.ranok.rx_bus.RxPosInfo;
import com.ranok.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;


public class ReceiptInfoVM extends BaseViewModel<ReceiptInfoIView>
        implements RecyclerBindingAdapter.OnItemClickListener<PosInReceiptModel>{

    private RecyclerBindingAdapter<PosInReceiptModel> adapter
            = new RecyclerBindingAdapter<>(R.layout.item_position_in_receipt, BR.viewModel, new ArrayList<>());

    public RecyclerBindingAdapter<PosInReceiptModel> getAdapter() {
        return adapter;
    }

    @Override
    public void onItemClick(int position, PosInReceiptModel item) {
        getViewOptional().showMenu(item);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        adapter.setOnItemClickListener(this);
        compositeDisposable.add(RxPosInfo.getInstance().getEvents().subscribe(this::gotNewData));
    }

    private void gotNewData(PositionInfoByBarcodeData positionInfoByBarcodeData) {
        List<PosInReceiptModel> list = positionInfoByBarcodeData.getPositionInReceiptList();
        if (list == null) list = new ArrayList<>();
        adapter.setItems(list);
    }
}
