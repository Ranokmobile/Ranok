package com.ranok.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import io.reactivex.disposables.CompositeDisposable;

public class Utils {
    public static void dismissDisposable(CompositeDisposable compositeDisposable){
        if (compositeDisposable != null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    public static void selectText(EditText et){
        et.setSelectAllOnFocus(true);
        et.requestFocus();
        //  et.setSelection(0,et.getText().toString().length());
        final InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void copyToClipboard(Context context, String text){
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        if (clipboard != null) clipboard.setPrimaryClip(clip);
    }
}
