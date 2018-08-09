package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranok.ui.dialogs.SelectDialogFragment;

@Entity
public class PositionInfoModel  implements SelectDialogFragment.Selectable {
    @SerializedName("id")
    @ColumnInfo(name="ID")
    int id;

    @SerializedName("code")
    @ColumnInfo(name="CODE")
    String code;

    @SerializedName("name")
    @ColumnInfo(name="NAME")
    String name;

    @SerializedName("prodCode")
    @ColumnInfo(name="prodCode")
    String prodCode;

    @ColumnInfo(name="prodCodeOld")
    String prodCodeOld;

    @SerializedName("owner")
    @ColumnInfo(name="owner")
    String owner;

    @SerializedName("packStandart")
    @ColumnInfo(name="packStandart")
    String packStandart;


    @Override
    public String getName() {
        return code + "-" + name + "-" + prodCode + "-" + prodCodeOld;
    }

    @Override
    public int getId() {
        return Integer.parseInt(code);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.prodCode);
        dest.writeString(this.prodCodeOld);
        dest.writeString(this.owner);
        dest.writeString(this.packStandart);
    }

    public PositionInfoModel() {
    }

    protected PositionInfoModel(Parcel in) {
        this.id = in.readInt();
        this.code = in.readString();
        this.name = in.readString();
        this.prodCode = in.readString();
        this.prodCodeOld = in.readString();
        this.owner = in.readString();
        this.packStandart = in.readString();
    }

    public static final Parcelable.Creator<PositionInfoModel> CREATOR = new Parcelable.Creator<PositionInfoModel>() {
        @Override
        public PositionInfoModel createFromParcel(Parcel source) {
            return new PositionInfoModel(source);
        }

        @Override
        public PositionInfoModel[] newArray(int size) {
            return new PositionInfoModel[size];
        }
    };
}

/* TYPE t_position is OBJECT
  ID NUMBER,
  CODE VARCHAR2(100),
  NAME  VARCHAR2(100),
  prodCode VARCHAR2(100),
  prodCodeOld  VARCHAR2(100),
  owner VARCHAR2(100),
  packStandart VARCHAR2(100)
*/