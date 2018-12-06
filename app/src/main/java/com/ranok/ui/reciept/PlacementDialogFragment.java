package com.ranok.ui.reciept;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ranok.ui.base.BaseActivity;
import com.ranok.ui.move_lpn.MoveLpnFragment;

import ranok.annotation.State;

public class PlacementDialogFragment extends DialogFragment {

    @State
    String lpn;

    public static PlacementDialogFragment getInstance(String sourceLpn){
        PlacementDialogFragment fragment = new PlacementDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lpn", sourceLpn);
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        StateHelperPlacementDialogFragment.onRestoreInstanceState(this, savedInstanceState);
        if (getArguments() != null){
            lpn = getArguments().getString("lpn");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Провести размещение НЗ " + lpn + " ?")
                .setPositiveButton("Да", (dialog, id) -> {
                    dialog.dismiss();
                    ((BaseActivity)getActivity()).addFragment(MoveLpnFragment.getInstance(lpn, true));
                })
                .setNegativeButton("Нет", (dialog, id) -> dialog.dismiss());
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateHelperPlacementDialogFragment.onSaveInstanceState(this, outState);
    }
}
