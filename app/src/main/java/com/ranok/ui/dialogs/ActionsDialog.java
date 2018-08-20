package com.ranok.ui.dialogs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranok.R;
import com.ranok.databinding.ActionsDialogFragmentBinding;

public class ActionsDialog extends DialogFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Действия");
        ActionsDialogFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.actions_dialog_fragment, null, false);
        binding.tvMove.setOnClickListener(this);
        binding.tvSplit.setOnClickListener(this);
        binding.tvSplitAndMove.setOnClickListener(this);
        binding.tvUnPack.setOnClickListener(this);
        return binding.getRoot();

    }

    @Override
    public void onClick(View view) {

    }
}
