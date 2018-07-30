package com.ranok.ui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.ranok.R;

public class LoaderDialog  extends ProgressDialog {

    public LoaderDialog(Context context) {
        super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    public LoaderDialog(Context context, int theme) {
        super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loader);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
    }
}
