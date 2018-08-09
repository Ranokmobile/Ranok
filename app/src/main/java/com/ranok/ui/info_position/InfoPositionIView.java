package com.ranok.ui.info_position;

import android.os.Parcelable;

import com.ranok.ui.base.BaseIView;

import java.util.ArrayList;


public interface InfoPositionIView extends BaseIView {
    void startScanBarcode();
    void showSelectPositionDialog(ArrayList<? extends Parcelable> sourceList);
}
