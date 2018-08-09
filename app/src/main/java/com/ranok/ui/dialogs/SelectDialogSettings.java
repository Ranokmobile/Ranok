package com.ranok.ui.dialogs;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class SelectDialogSettings extends BaseObservable implements Parcelable {

    private String header;

//    public String getString(String stringKey) {
//        return getLocalizedString(stringKey);
//    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getHeaderVisibility() {
        if (header == null || header.isEmpty()) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }

    public SelectDialogSettings(String header) {
        this.header = header;
    }


    public SelectDialogSettings() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.header);
    }

    protected SelectDialogSettings(Parcel in) {
        this.header = in.readString();
    }

    public static final Creator<SelectDialogSettings> CREATOR = new Creator<SelectDialogSettings>() {
        @Override
        public SelectDialogSettings createFromParcel(Parcel source) {
            return new SelectDialogSettings(source);
        }

        @Override
        public SelectDialogSettings[] newArray(int size) {
            return new SelectDialogSettings[size];
        }
    };
}
