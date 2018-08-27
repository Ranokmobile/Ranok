package com.ranok.ui.base.search_widget;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;

import static com.ranok.ui.base.search_widget.BaseSearchWidgetVM.TextStatus.INVALID;
import static com.ranok.ui.base.search_widget.BaseSearchWidgetVM.TextStatus.VALID;

public abstract class BaseSearchWidgetVM extends BaseObservable {

    private static final int RES_INVALID = R.drawable.ic_warning, RES_VALID =  R.drawable.ok_appproval_acceptance;

    private int maxTextLength;
    private String inputText;
    private int searchStatusResource = RES_INVALID;
    private TextStatus textStatus = INVALID;
    private String hawkTag;
    private SearchWidgetCallbacks callbacks;
    private boolean searching;
    private String mask;
    private boolean searchBtnVisibility=true;

    BaseSearchWidgetVM(int maxTextLength, String hawkTag, SearchWidgetCallbacks callbacks, String mask) {
        this.maxTextLength = maxTextLength;
        this.hawkTag = hawkTag;
        this.callbacks = callbacks;
        this.mask = mask;
//        if (Hawk.contains(hawkTag)){
//            onTextChanged(Hawk.get(hawkTag), 0,0,0);
//        }
    }

    public BaseSearchWidgetVM(int maxTextLength, String hawkTag, SearchWidgetCallbacks callbacks, String mask, boolean searchBtnVisibility) {
        this(maxTextLength, hawkTag, callbacks, mask);
        this.searchBtnVisibility = searchBtnVisibility;
    }


    public void onClearClick(View v){
        onTextChanged("", 0, 0, 0);
    }


    @Bindable
    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
        notifyPropertyChanged(BR.mask);
    }

    @Bindable
    public boolean isSearchBtnVisibility() {
        return searchBtnVisibility;
    }

    public void setSearchBtnVisibility(boolean searchBtnVisibility) {
        this.searchBtnVisibility = searchBtnVisibility;
        notifyPropertyChanged(BR.searchBtnVisibility);
    }

    @Bindable
    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        notifyPropertyChanged(BR.inputText);
    }



    @Bindable
    public boolean isSearching() {
        return searching;
      //  notifyPropertyChanged(BR.progress);
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    @Bindable
    public int getSearchStatusResource() {
        return searchStatusResource;
    }

    public void setSearchStatusResource(int searchStatusResource) {
        this.searchStatusResource = searchStatusResource;
        notifyPropertyChanged(BR.searchStatusResource);
    }

    @Bindable
    public int getMaxTextLength() {
        return maxTextLength;
    }

    public void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }

    public TextStatus getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(TextStatus textStatus) {
        this.textStatus = textStatus;
        setSearchStatusResource(textStatus == VALID ? RES_VALID : RES_INVALID);
    }

    public void onClick(View v){
//        if (v.getId() ==  R.id.ibSearch) {
//            Hawk.put(hawkTag, inputText);
//        }
        if (v.getId()==R.id.ibSearch && !isInputCorrect()) return;
        if (callbacks != null) callbacks.onClickWidgetSearch(v);
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
        setInputText(text.toString());
        if (isInputCorrect()) { // && textStatus==INVALID
            setTextStatus(VALID);
        } else if (!isInputCorrect()) { // && textStatus==VALID
            setTextStatus(INVALID);
        }
    }

    protected abstract boolean isInputCorrect();

    public enum TextStatus{
        VALID, INVALID
    }
}
