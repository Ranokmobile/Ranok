package com.ranok.ui.move_lpn;

import com.ranok.R;

public class MoveLpnModel {
    private String sourceLpn, targetLpn, targetPlace, newLpnPlace;

    private int[] arrRbAim = {R.id.rbToLpn, R.id.rbToPlace, R.id.rbToNewLpn};
    private String[] arrAimCaptions = {"Целевой НЗ", "Целевая ячейка", ""};
    private int selectedAim;


    private int[] arrRbType = {R.id.rbFullLpn, R.id.rbLpnContent};
    private int selectedType;

    public int getSelectedAimViewId(){
        return arrRbAim[selectedAim];
    }

    public int getSelectedTypeViewId(){
        return arrRbType[selectedType];
    }

    public String getAimTargetCaption(){
        return arrAimCaptions[selectedAim];
    }


    public int[] getArrRbAim() {
        return arrRbAim;
    }

    public void setArrRbAim(int[] arrRbAim) {
        this.arrRbAim = arrRbAim;
    }

    public int getSelectedAim() {
        return selectedAim;
    }

    public void setSelectedAim(int selectedAim) {
        this.selectedAim = selectedAim;
    }

    public int[] getArrRbType() {
        return arrRbType;
    }

    public void setArrRbType(int[] arrRbType) {
        this.arrRbType = arrRbType;
    }

    public int getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(int selectedType) {
        this.selectedType = selectedType;
    }

    public String getSourceLpn() {
        return sourceLpn;
    }

    public void setSourceLpn(String sourceLpn) {
        this.sourceLpn = sourceLpn;
    }

    public String getTargetLpn() {
        return targetLpn;
    }

    public void setTargetLpn(String targetLpn) {
        this.targetLpn = targetLpn;
    }

    public String getTargetPlace() {
        return targetPlace;
    }

    public void setTargetPlace(String targetPlace) {
        this.targetPlace = targetPlace;
    }

    public String getNewLpnPlace() {
        return newLpnPlace;
    }

    public void setNewLpnPlace(String newLpnPlace) {
        this.newLpnPlace = newLpnPlace;
    }
}
