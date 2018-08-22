package com.ranok.ui.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranok.R;
import com.ranok.databinding.ActionsDialogFragmentBinding;
import com.ranok.models.ActionModel;
import com.ranok.network.models.PlaceInfoModel;

import java.util.ArrayList;

import ranok.annotation.State;

public class ActionsDialog extends DialogFragment  {

    @State
    ArrayList<ActionModel> actions;

    @State
    PlaceInfoModel item;

    @State
    String header;

    View.OnClickListener clickListener = view -> processClick(view.getId());

    public static ActionsDialog getInstance(ArrayList<ActionModel> actions, String header, PlaceInfoModel item){
        ActionsDialog actionsDialog = new ActionsDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LIST", actions);
        bundle.putString("HEADER", header);
        bundle.putParcelable("ITEM", item);
        actionsDialog.setArguments(bundle);
        return actionsDialog;
    }

    public void processClick(int id){
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra("id", id);
            intent.putExtra("HEADER", header);
            intent.putExtra("ITEM", item);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
        dismiss();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null && args.getParcelableArrayList("LIST") != null) {
            actions = args.getParcelableArrayList("LIST");
            header = args.getString("HEADER");
            item = args.getParcelable("ITEM");
        }
        if (savedInstanceState != null){
            StateHelperActionsDialog.onRestoreInstanceState(this,savedInstanceState);
        }

        ActionsDialogFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.actions_dialog_fragment, null, false);
        binding.header.setText(header);
        for (ActionModel model : actions) addActionView(model, binding.llRoot);
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateHelperActionsDialog.onSaveInstanceState(this, outState);
    }

    private void addActionView(ActionModel actionModel, LinearLayout llParent){
        if (!actionModel.isVisible()) return;

        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params.setMargins(16, 24, 16, 24);
        params.gravity= Gravity.CENTER;
        tv.setLayoutParams(params);
        tv.setPadding(24,24,24,16);
        tv.setText(actionModel.getText());
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);

        tv.setLayoutParams(params);
        tv.setId(actionModel.getId());
        tv.setEnabled(actionModel.isEnabled());
        tv.setOnClickListener(clickListener);
        Drawable icon = getResources().getDrawable(actionModel.getDrawableId());
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        tv.setCompoundDrawablesRelative(icon, null, null, null );
        tv.setCompoundDrawablePadding(16);
        llParent.addView(tv);
    }

}
