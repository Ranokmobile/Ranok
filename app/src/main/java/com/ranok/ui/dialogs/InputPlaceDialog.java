package com.ranok.ui.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranok.R;
import com.ranok.databinding.InputPlaceDialogFragmentBinding;
import com.ranok.ui.base.BaseActivity;

import ranok.annotation.State;

public class InputPlaceDialog extends DialogFragment {

    @State
    String header;

    public static final String PLACE = "PLACE";

    InputPlaceDialogFragmentBinding binding;


    public static InputPlaceDialog getInstance(String header){
        InputPlaceDialog dialog = new InputPlaceDialog();
        Bundle bundle = new Bundle();
        bundle.putString("HEADER", header);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null && args.getString("HEADER") != null) {
            header = args.getString("HEADER");
        }
        if (savedInstanceState != null){
            StateHelperInputPlaceDialog.onRestoreInstanceState(this,savedInstanceState);
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.input_place_dialog_fragment, null, false);
        binding.header.setText(header);
        binding.buttonOk.setOnClickListener(this::onOkClick);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)        ( (BaseActivity) getActivity()).showKeyboard();
    }

    private void onOkClick(View view) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(PLACE, binding.etCode.getText().toString() );
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
        dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateHelperInputPlaceDialog.onSaveInstanceState(this, outState);
    }
}
