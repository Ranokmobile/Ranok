package com.ranok.ui.dialogs;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.ranok.R;

import java.util.ArrayList;
import java.util.Set;

public abstract class DialogBuilder<T extends DialogFragment> {
    public static final String SOURCE_LIST = "SOURCE_LIST", SELECTED_ITEMS = "SELECTED_ITEMS", RESULTS_LIST = "RESULTS", HEADER_TEXT = "HEADER",
            ITEM_LAYOUT = "ITEM_LAYOUT";

    protected final Bundle arguments;
    public DialogBuilder() {
        arguments = new Bundle();
    }
    public DialogBuilder setHeaderText(String name) {
        arguments.putString(HEADER_TEXT, name);
        return this;
    }
    public DialogBuilder setSourceList(ArrayList<? extends Parcelable> sourceList) {
        arguments.putParcelableArrayList(SOURCE_LIST, sourceList);
        return this;
    }
    public DialogBuilder setSelectedItems(Set<Integer> selectedItems) {
        ArrayList<Integer> s = new ArrayList<>();
        s.addAll(selectedItems);
        arguments.putIntegerArrayList(SELECTED_ITEMS, s);
        return this;
    }
    public DialogBuilder setItemLayout(int itemLayout) {
        arguments.putInt(ITEM_LAYOUT, itemLayout);
        return this;
    }
    //public ViewDataBinding
    public T build(Fragment target, int requestCode) {
        T fragment = getInstance();
        fragment.setArguments(arguments);
        fragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog);
        fragment.setTargetFragment(target, requestCode);
        return fragment;
    }
    public abstract T getInstance();
}
