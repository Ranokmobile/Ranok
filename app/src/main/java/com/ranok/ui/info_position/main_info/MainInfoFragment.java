package com.ranok.ui.info_position.main_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.MainInfoFragmentBinding;
import com.ranok.enums.Actions;
import com.ranok.models.ActionModel;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.ActionsDialog;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.ui.pack_lpn.PackLpnFragment;
import com.ranok.ui.print_lpn.PrintLpnFragment;
import com.ranok.ui.split_lpn.SplitLpnFragment;
import com.ranok.ui.unpack_lpn.UnpackLpnFragment;
import com.ranok.utils.LpnUtils;

import java.util.ArrayList;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class MainInfoFragment extends BaseFragment<MainInfoIView, MainInfoVM, MainInfoFragmentBinding> implements MainInfoIView {

    public static final int ACTION_DIALOG_CODE = 32;

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

    @Override
    public void showMenu(PlaceInfoModel item) {
        ArrayList<ActionModel> list = new ArrayList<>();

        if (LpnUtils.isMoveEnabled(item)) list.add(new ActionModel(Actions.MOVE));
        if (LpnUtils.isSplitEnabled(item)) list.add(new ActionModel(Actions.SPLIT));
        if (LpnUtils.isPrintEnabled(item)) list.add(new ActionModel(Actions.PRINT));
        if (LpnUtils.isUnpackEnabled(item)) list.add(new ActionModel(Actions.UNPACK));
        if (LpnUtils.isPackEnabled(item)) list.add(new ActionModel(Actions.PACK));

        String header = (item.getLpn() == null || item.getLpn().isEmpty()) ? item.getItemCode() : item.getLpn();

        ActionsDialog actionsDialog = ActionsDialog.getInstance(list, header, item);
        actionsDialog.setTargetFragment(this, ACTION_DIALOG_CODE);
        FragmentManager fm = this.getFragmentManager();
        if (fm != null) actionsDialog.show(fm, "DLG");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_DIALOG_CODE && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra("id", 0);
            String header = data.getStringExtra("HEADER");
            PlaceInfoModel item = data.getParcelableExtra("ITEM");
            processAction(id, item);
        }
    }

    private void processAction(int id, PlaceInfoModel item) {
        Actions action = Actions.getById(id);
        switch (action) {
            case MOVE: mActivity.addFragment(MoveLpnFragment.getInstance(item.getLpn()));
                break;
            case PACK:  mActivity.addFragment(PackLpnFragment.getInstance(item));
                break;
            case PRINT: mActivity.addFragment(PrintLpnFragment.getInstance(item));
                break;
            case SPLIT: mActivity.addFragment(SplitLpnFragment.getInstance(item));
                break;
            case UNPACK: mActivity.addFragment(UnpackLpnFragment.getInstance(item));
                break;

        }
    }

    @Nullable
    @Override
    public Class<MainInfoVM> getViewModelClass() {
        return MainInfoVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.main_info_fragment, BR.viewModel, getContext());
    }
}
