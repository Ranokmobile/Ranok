package com.ranok.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ranok.enums.Actions;

public class ActionModel implements IActionModel, Parcelable {
    private int id, drawableId;
    private String text;
    private boolean visible, enabled;

    public ActionModel(int id, int drawableId, String text, boolean visible, boolean enabled) {
        this.id = id;
        this.drawableId = drawableId;
        this.text = text;
        this.visible = visible;
        this.enabled = enabled;
    }

    public ActionModel(Actions enumActions) {
        this.id = enumActions.getId();
        this.drawableId = enumActions.getDrawableId();
        this.text = enumActions.getText();
        this.visible = true;
        this.enabled = true;
    }

    @Override
    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.drawableId);
        dest.writeString(this.text);
        dest.writeByte(this.visible ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
    }

    protected ActionModel(Parcel in) {
        this.id = in.readInt();
        this.drawableId = in.readInt();
        this.text = in.readString();
        this.visible = in.readByte() != 0;
        this.enabled = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ActionModel> CREATOR = new Parcelable.Creator<ActionModel>() {
        @Override
        public ActionModel createFromParcel(Parcel source) {
            return new ActionModel(source);
        }

        @Override
        public ActionModel[] newArray(int size) {
            return new ActionModel[size];
        }
    };
}
