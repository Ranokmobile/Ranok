package com.ranok.network.models;

import android.arch.persistence.room.ColumnInfo;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ranok.BR;
import com.ranok.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PositionLotAttributesModel extends BaseObservable implements Parcelable {
    @SerializedName("lotNumber")
    @ColumnInfo(name="lot_number")
    String lotNumber;

    @SerializedName("posLength")
    @ColumnInfo(name="pos_length")
    String posLength;

    @SerializedName("posWidth")
    @ColumnInfo(name="pos_width")
    String posWidth;

    @SerializedName("posHeight")
    @ColumnInfo(name="pos_height")
    String posHeight;

    @SerializedName("posWeight")
    @ColumnInfo(name="pos_weight")
    String posWeight;

    @SerializedName("posHardness")
    @ColumnInfo(name="pos_hardness")
    String posHardness;

    @SerializedName("packLength")
    @ColumnInfo(name="pack_length")
    String packLength;

    @SerializedName("packWidth")
    @ColumnInfo(name="pack_width")
    String packWidth;

    @SerializedName("packHeight")
    @ColumnInfo(name="pack_height")
    String packHeight;

    @SerializedName("packWeight")
    @ColumnInfo(name="pack_weight")
    String packWeight;

    @SerializedName("packType")
    @ColumnInfo(name="pack_type")
    String packType;

    @SerializedName("packHardness")
    @ColumnInfo(name="pack_hardness")
    String packHardness;

    @SerializedName("packStandart")
    @ColumnInfo(name="pack_standart")
    String packStandart;

    @SerializedName("createdBy")
    @ColumnInfo(name="createdBy")
    String createdBy;

    @SerializedName("creationDate")
    @ColumnInfo(name="creationDate")
    String creationDate;

    @SerializedName("lastUpdatedBy")
    @ColumnInfo(name="lastUpdatedBy")
    String lastUpdatedBy;

    @SerializedName("lastUpdatedDate")
    @ColumnInfo(name="lastUpdatedDate")
    String lastUpdatedDate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Bindable
    public String getLotNumber() {
        return lotNumber;
    }

    @Bindable
    public String getPosLength() {
        return posLength;
    }
    @Bindable
    public String getPosLengthb() {
        return posLength + " см.";
    }



    @Bindable
    public String getPosWidth() {
        return posWidth;
    }
    @Bindable
    public String getPosWidthb() {
        return posWidth+ " см.";
    }


    @Bindable
    public String getPosHeight() {
        return posHeight;
    }
    @Bindable
    public String getPosHeightb() {
        return posHeight + " см.";
    }

    @Bindable
    public String getPosWeight() {
        return posWeight;
    }
    @Bindable
    public String getPosWeightb() {
        return posWeight+ " кг.";
    }


    @Bindable
    public String getPosHardness() {
        return posHardness;
    }
    @Bindable
    public String getPackLength() {
        return packLength;
    }
    @Bindable
    public String getPackLengthb() {
        return packLength+ " см.";
    }

    @Bindable
    public String getPackWidth() {
        return packWidth;
    }
    @Bindable
    public String getPackWidthb() {
        return packWidth+ " см.";
    }


    @Bindable
    public String getPackHeight() {
        return packHeight;
    }
    @Bindable
    public String getPackHeightb() {
        return packHeight+ " см.";
    }

    @Bindable
    public String getPackWeight() {
        return packWeight;
    }
    @Bindable
    public String getPackWeightb() {
        return packWeight+ " кг.";
    }

    @Bindable
    public String getPackType() {
        return packType;
    }
    @Bindable
    public String getPackHardness() {
        return packHardness;
    }
    @Bindable
    public String getPackStandart() {
        return packStandart;
    }
    @Bindable
    public String getCreatedBy() {
        return createdBy;
    }
    @Bindable
    public String getCreationDate() {
        return creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public void setPosLength(String posLength) {
        //if (posLength.substring(0,1).equals("."))
        this.posLength = preprocessString(posLength);
        notifyPropertyChanged(BR.posLengthUnset);
    }
    @Bindable
    public boolean isPosLengthUnset(){
        return isUnsetNumber(posLength);
    }

    public void setPosWidth(String posWidth) {
        this.posWidth = preprocessString(posWidth);
        notifyPropertyChanged(BR.posWidthhUnset);
    }

    @Bindable
    public boolean isPosWidthhUnset(){
        return isUnsetNumber(posWidth);
    }

    public void setPosHeight(String posHeight) {
        this.posHeight = preprocessString(posHeight);
        notifyPropertyChanged(BR.posHeightUnset);
    }

    @Bindable
    public boolean isPosHeightUnset(){
        return isUnsetNumber(posHeight);
    }

    public void setPosWeight(String posWeight) {
        this.posWeight = preprocessString(posWeight);
        notifyPropertyChanged(BR.posWeightUnset);
    }

    @Bindable
    public boolean isPosWeightUnset(){
        return isUnsetNumber(posWeight);
    }

    public void setPosHardness(String posHardness) {
        this.posHardness = posHardness;
    }

    public void setPackLength(String packLength) {
        this.packLength = preprocessString(packLength);
        notifyPropertyChanged(BR.packLengthUnset);
    }

    @Bindable
    public boolean isPackLengthUnset(){
        return isUnsetNumber(packLength);
    }

    public void setPackWidth(String packWidth) {
        this.packWidth = preprocessString(packWidth);
        notifyPropertyChanged(BR.packWidthUnset);
    }

    @Bindable
    public boolean isPackWidthUnset(){
        return isUnsetNumber(packWidth);
    }

    public void setPackHeight(String packHeight) {
        this.packHeight = preprocessString(packHeight);
        notifyPropertyChanged(BR.packHeightUnset);
    }

    @Bindable
    public boolean isPackHeightUnset(){
        return isUnsetNumber(packHeight);
    }

    public void setPackWeight(String packWeight) {
        this.packWeight = preprocessString(packWeight);
        notifyPropertyChanged(BR.packWeightUnset);
    }
    @Bindable
    public boolean isPackWeightUnset(){
        return isUnsetNumber(packWeight);
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public void setPackHardness(String packHardness) {
        this.packHardness = packHardness;
    }

    public void setPackStandart(String packStandart) {
        this.packStandart = packStandart;
        notifyPropertyChanged(BR.packStandartUnset);
    }

    @Bindable
    public boolean isPackStandartUnset(){
        return isUnsetNumber(packStandart);
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public boolean isUnsetNumber(String s){
        return StringUtils.isEmpty(s) || s.equals(".") || Float.valueOf(s) == 0;
    }

    private String preprocessString(String s){
        String ret = s;
        if (s.isEmpty()) return ret;
        ret =  (s.substring(0,1).equals(".") ? "0" : "") + s;
        return ret;
    }

    public String isDataCorrect(String positionPackStandart){
        if (isPackHeightUnset()) return "Высота упаковки не установлена";
        if (isPackLengthUnset()) return "Длина упаковки не установлена";
        if (isPackStandartUnset()) return "Стандарт упаковки не установлен";
        if (isPackWeightUnset()) return "Масса упаковки не установлен";
        if (isPackWidthUnset()) return "Ширина упаковки не установлена";
        if (isPosHeightUnset()) return "Высота штуки не установлена";
        if (isPosLengthUnset()) return "Длина штуки не установлена";
        if (isPosWeightUnset()) return "Масса штуки не установлен";
        if (isPosWidthhUnset()) return "Ширина штуки не установлена";
        if (StringUtils.isEmpty(posHardness)) return "Твердость штуки не установлена";
        if (StringUtils.isEmpty(packHardness)) return "Твердость упаковки не установлена";
        if (StringUtils.isEmpty(packType)) return "Тип упаковки не установлен";

        Observable<Float> packObservable = Observable.just(packHeight, packWidth, packLength)
                .map(Float::valueOf)
                .sorted();

        Observable<Float> posObservable = Observable.just(posHeight, posWidth, posLength)
                .map(Float::valueOf)
                .sorted();

        //Проверка от большого к меньшему размеров по 1
        Single<Long> smallToBig = Observable.zip(packObservable, posObservable
                , (aFloat, aFloat2) -> (aFloat < aFloat2)).filter(i->i).count();
        List<String> s = new ArrayList<>();
        CompositeDisposable cd = new CompositeDisposable();
         cd.add(smallToBig.subscribe(aLong -> {
             if (aLong>0) s.add("Размеры штуки больше размеров пачки");
         })
        );
        if (s.size()>0) return s.get(0);

        //Проверка объема
        Single<Float> packTotal = packObservable.reduce(1f, (total, val) -> total*val);
        Single<Float> posTotal = posObservable.reduce(1f, (total, val) -> total*val);
        Single<Long> totalSize = Single.zip(packTotal, posTotal
                , (aFloat, aFloat2) -> (aFloat < aFloat2)).filter(i->i).count();
        s.clear();
        cd.add(totalSize.subscribe(aLong -> {
                    if (aLong>0) s.add("Объем штуки больше объема пачки");
                })
        );
        if (s.size()>0) return s.get(0);

        //
        List<Float> floatList = new ArrayList<>();
        cd.add(posTotal.subscribe((Consumer<Float>) floatList::add));
        cd.add(packTotal.subscribe((Consumer<Float>) floatList::add));

        Float div = floatList.get(0)*Float.valueOf(positionPackStandart) / floatList.get(1);

        if ( div <.85 || div >1.0){
            return "Объем штучек*стандарт не соответствует объему пачки";
        }


        if (Float.valueOf(posWeight) *Float.valueOf(positionPackStandart) > Float.valueOf(packWeight))
            return "Масса штук больше массы пачки";

        if (!cd.isDisposed()) cd.dispose();
        return "";

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lotNumber);
        dest.writeString(this.posLength);
        dest.writeString(this.posWidth);
        dest.writeString(this.posHeight);
        dest.writeString(this.posWeight);
        dest.writeString(this.posHardness);
        dest.writeString(this.packLength);
        dest.writeString(this.packWidth);
        dest.writeString(this.packHeight);
        dest.writeString(this.packWeight);
        dest.writeString(this.packType);
        dest.writeString(this.packHardness);
        dest.writeString(this.packStandart);
        dest.writeString(this.createdBy);
        dest.writeString(this.creationDate);
        dest.writeString(this.lastUpdatedBy);
        dest.writeString(this.lastUpdatedDate);
        dest.writeByte(this.expanded ? (byte) 1 : (byte) 0);
    }

    public PositionLotAttributesModel() {
    }

    protected PositionLotAttributesModel(Parcel in) {
        this.lotNumber = in.readString();
        this.posLength = in.readString();
        this.posWidth = in.readString();
        this.posHeight = in.readString();
        this.posWeight = in.readString();
        this.posHardness = in.readString();
        this.packLength = in.readString();
        this.packWidth = in.readString();
        this.packHeight = in.readString();
        this.packWeight = in.readString();
        this.packType = in.readString();
        this.packHardness = in.readString();
        this.packStandart = in.readString();
        this.createdBy = in.readString();
        this.creationDate = in.readString();
        this.lastUpdatedBy = in.readString();
        this.lastUpdatedDate = in.readString();
        this.expanded = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PositionLotAttributesModel> CREATOR = new Parcelable.Creator<PositionLotAttributesModel>() {
        @Override
        public PositionLotAttributesModel createFromParcel(Parcel source) {
            return new PositionLotAttributesModel(source);
        }

        @Override
        public PositionLotAttributesModel[] newArray(int size) {
            return new PositionLotAttributesModel[size];
        }
    };
}