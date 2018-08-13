package com.ranok.ui.base.search_widget;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.hawk.Hawk;
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

    BaseSearchWidgetVM(int maxTextLength, String hawkTag, SearchWidgetCallbacks callbacks) {
        this.maxTextLength = maxTextLength;
        this.hawkTag = hawkTag;
        this.callbacks = callbacks;
        if (Hawk.contains(hawkTag)){
            onTextChanged(Hawk.get(hawkTag), 0,0,0);
        }
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
        if (v.getId() ==  R.id.ibSearch) {
            Hawk.put(hawkTag, inputText);
        }
        callbacks.onClickWidgetSearch(v);
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
