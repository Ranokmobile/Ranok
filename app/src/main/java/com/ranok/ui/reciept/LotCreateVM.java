package com.ranok.ui.reciept;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ranok.network.models.PositionLotAttributesModel;
import com.ranok.network.models.RecieptListModel;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.request.CreateLotRequest;
import com.ranok.network.response.CreateLotResponse;
import com.ranok.network.response.LotInfoResponse;
import com.ranok.rx_bus.RxLotCreated;
import com.ranok.ui.base.BaseViewModel;
import com.ranok.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ranok.annotation.State;

import static com.ranok.ui.reciept.LotCreateFragment.CHANGE_LOT;
import static com.ranok.ui.reciept.LotCreateFragment.NEW_LOT;


public class LotCreateVM extends BaseViewModel<LotCreateIView> {

    public String[] hardness = {"Твердое", "Средне", "Мягкое", ""};
    public String[] packType = {"Пачка", "Короб", "Пленка", ""};

    @State
    int type;
    @State
    String lot;
    @State
    RecieptListModel position;
    @State
    List<PositionLotAttributesModel> lotsList = new ArrayList<>();
    @State
    PositionLotAttributesModel model;

    public String[] getHardness() {
        return hardness;
    }

    public String[] getPackType() {
        return packType;
    }

    public PositionLotAttributesModel getModel() {
        return model;
    }

    public RecieptListModel getPosition() {
        return position;
    }

    public String getLot() {
        return lot;
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
        model = new PositionLotAttributesModel();
        StateHelperLotCreateVM.onRestoreInstanceState(this, savedInstanceState);
        if (arguments != null) {
            type = arguments.getInt("type");
            lot = arguments.getString("lot");
            position = arguments.getParcelable("position");
        }
        getLots();
    }


    private void getLots() {
        showLoader();
        compositeDisposable.add(
                netApi.getLotInfo(new CodeRequest(position.getItemCode(),""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processResponse, this::processError)
        );
    }

    @Bindable
    public boolean isBtnVisible(){
        return type==1;
    }

    private void processResponse(LotInfoResponse lotInfoResponse) {
        hideLoader();
        lotsList = lotInfoResponse.data.getLots();
        if (type == NEW_LOT) {
            int lotNum = 1;
            for (PositionLotAttributesModel lotModel : lotsList){
                int currentNum = StringUtils.lotToNumber(lotModel.getLotNumber());
                if (currentNum>lotNum) lotNum=currentNum;
            }
            if (lotNum==1) lotNum = 10000; else lotNum++;
            lot = StringUtils.numberToLot(lotNum);
            model.setLotNumber(lot);
            getViewOptional().setSpinnerPosHardnessSelection(3);
            getViewOptional().setSpinnerPackHardnessSelection(3);
            getViewOptional().setSpinnerPackStandartSelection(3);
            notifyChange();
        } else if (type == CHANGE_LOT){
            for (PositionLotAttributesModel lotModel : lotsList){
                if (lotModel.getLotNumber().equals(lot)) model=lotModel;
            }
            for (int i=0; i < hardness.length; i++){
                if (hardness[i].equals(model.getPosHardness()))
                    getViewOptional().setSpinnerPosHardnessSelection(i);
            }
            for (int i=0; i < hardness.length; i++){
                if (hardness[i].equals(model.getPackHardness()))
                    getViewOptional().setSpinnerPackHardnessSelection(i);
            }
            for (int i = 0; i < packType.length; i++) {
                if (packType[i].equals(model.getPackStandart()))
                    getViewOptional().setSpinnerPackStandartSelection(i);
            }
            notifyChange();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        StateHelperLotCreateVM.onSaveInstanceState(this, outState);
    }

    public void onPosHardhessSelected(int i){
        model.setPosHardness(hardness[i]);

    }

    public void onPackHardhessSelected(int i){
        model.setPackHardness(hardness[i]);
    }

    public void onPackTypeSelected(int i){
        model.setPackType(packType[i]);
    }

    public void onClick(View v){
        String check = model.isDataCorrect(position.getPackStandart());
        if (StringUtils.isEmpty(check)) {
            showLoader();
            compositeDisposable.add(
                    netApi.createLot(new CreateLotRequest(Integer.parseInt(position.getItemCode())
                    ,model.getLotNumber(), model.getPosLength()
                    ,model.getPosWidth(), model.getPosHeight(), model.getPosWeight(), model.getPosHardness()
                    ,model.getPackLength(), model.getPackWidth(), model.getPackHeight(), model.getPackWeight()
                    ,model.getPackType(), model.getPackHardness(), model.getPackStandart()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::processCreateLotResponse, this::processError)
            );
        } else {
            getViewOptional().showSnakeBar(check);
        }
    }

    private void processCreateLotResponse(CreateLotResponse createLotResponse) {
        hideLoader();
        if (createLotResponse.data.resultCode == 0) {
            RxLotCreated.getInstance().sendLpnData(createLotResponse);
            getViewOptional().closeScreen();
        } else {
            getViewOptional().showSnakeBar(createLotResponse.data.resultMessage);
        }

    }

}
