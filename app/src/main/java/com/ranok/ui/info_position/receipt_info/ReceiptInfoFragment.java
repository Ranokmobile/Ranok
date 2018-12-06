package com.ranok.ui.info_position.receipt_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.ReceiptInfoFragmentBinding;
import com.ranok.enums.Actions;
import com.ranok.models.ActionModel;
import com.ranok.network.models.PosInReceiptModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.ActionsDialog;
import com.ranok.ui.info_lpn.InfoLpnFragment;
import com.ranok.ui.reciept.CheckRecieptFragment;
import com.ranok.utils.StringUtils;

import java.util.ArrayList;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class ReceiptInfoFragment extends BaseFragment<ReceiptInfoIView, ReceiptInfoVM, ReceiptInfoFragmentBinding> implements ReceiptInfoIView {

    public static final int ACTION_DIALOG_CODE = 3;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        RecyclerView rv = getBinding().rv;
        RecyclerView.Adapter adapter = getViewModel().getAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_horizontal_gray));
        rv.addItemDecoration(decor);
        if (adapter != null) rv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public Class<ReceiptInfoVM> getViewModelClass() {
        return ReceiptInfoVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.receipt_info_fragment, BR.viewModel, getContext());
    }

    @Override
    public void showMenu(PosInReceiptModel item) {
        ArrayList<ActionModel> list = new ArrayList<>();
        if (!item.getTransactionTypeName().equalsIgnoreCase("Контроль")) {
            list.add(new ActionModel(Actions.RECIEPT));
        }
        list.add(new ActionModel(Actions.LPN_INFO));
       // if (LpnUtils.isPlaceInfoEnabled(item)) list.add(new ActionModel(Actions.PLACE_INFO));
        String header = (item.getLpn());
        ActionsDialog actionsDialog = ActionsDialog.getInstance(list, header, item);
        actionsDialog.setTargetFragment(this, ACTION_DIALOG_CODE);
        actionsDialog.show(getParentFragment().getChildFragmentManager(), "DLG");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_DIALOG_CODE && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra("id", 0);
            String header = data.getStringExtra("HEADER");
            PosInReceiptModel item = data.getParcelableExtra("ITEMRECIEPT");
            processAction(id, item);
        }
    }

    private void processAction(int id, PosInReceiptModel item) {
        Actions action = Actions.getById(id);
        switch (action) {
            case LPN_INFO:
                mActivity.addFragment(InfoLpnFragment.getInstance(StringUtils.formatFromLpn(item.getLpn())));
                break;
            case RECIEPT:
                mActivity.addFragment(CheckRecieptFragment.getInstance(StringUtils.formatFromLpn(item.getLpn())));
                break;
        }
    }




    }
