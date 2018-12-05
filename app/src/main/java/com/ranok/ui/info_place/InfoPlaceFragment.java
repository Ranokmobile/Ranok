package com.ranok.ui.info_place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.InfoPlaceFragmentBinding;
import com.ranok.enums.Actions;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.models.ActionModel;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.ActionsDialog;
import com.ranok.ui.info_lpn.InfoLpnFragment;
import com.ranok.ui.info_position.InfoPositionFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.ui.pack_lpn.PackLpnFragment;
import com.ranok.ui.print_lpn.PrintLpnFragment;
import com.ranok.ui.split_lpn.SplitLpnFragment;
import com.ranok.ui.unpack_lpn.UnpackLpnFragment;
import com.ranok.utils.LpnUtils;
import com.ranok.utils.StringUtils;
import com.ranok.utils.Utils;

import java.util.ArrayList;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoPlaceFragment extends BaseFragment<InfoPlaceIView, InfoPlaceVM, InfoPlaceFragmentBinding>
        implements InfoPlaceIView, TextView.OnEditorActionListener {
    private final static int ACTION_DIALOG_CODE = 3;

    private PopupMenu popupActions;

    public static InfoPlaceFragment getInstance(String place){
        InfoPlaceFragment fragment = new InfoPlaceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("place", place);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getScreenTitle() {
        return "Информация о ячейке";
    }

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
        EditText et = getBinding().searchItem.etCode;
        et.post(() -> Utils.selectText(et));
        et.setOnEditorActionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().startSearch();
    }

    @Override
    public void showMenu(PlaceInfoModel item) {
        ArrayList<ActionModel> list = new ArrayList<>();

        if (LpnUtils.isMoveEnabled(item)) list.add(new ActionModel(Actions.MOVE));
        if (LpnUtils.isSplitEnabled(item)) list.add(new ActionModel(Actions.SPLIT));
        if (LpnUtils.isPrintEnabled(item)) list.add(new ActionModel(Actions.PRINT));
        if (LpnUtils.isUnpackEnabled(item)) list.add(new ActionModel(Actions.UNPACK));
        if (LpnUtils.isPackEnabled(item)) list.add(new ActionModel(Actions.PACK));
        if (LpnUtils.isLpnInfoEnabled(item)) list.add(new ActionModel(Actions.LPN_INFO));
        if (LpnUtils.isPositionInfoEnabled(item)) list.add(new ActionModel(Actions.POSITION_INFO));


        String header = (item.getLpn() == null || item.getLpn().isEmpty()) ? item.getItemCode() : item.getLpn();

        ActionsDialog actionsDialog = ActionsDialog.getInstance(list, header, item);
        actionsDialog.setTargetFragment(this, ACTION_DIALOG_CODE);
        actionsDialog.show(mActivity.getSupportFragmentManager(), "DLG");

    }

    @Override
    public void startScanBarcode() {
        startActivityForResult(new Intent(getActivity(), LivePreviewActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String barcode = data.getStringExtra("barcode");
            getViewModel().gotBarcode(barcode);
        }
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
            case LPN_INFO: mActivity.addFragment(InfoLpnFragment.getInstance(StringUtils.formatFromLpn(item.getLpn())));
                break;
            case POSITION_INFO: mActivity.addFragment(InfoPositionFragment.getInstance(item.getItemCode()));
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mActivity.hideKeyboard();
            getViewModel().startSearch();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Class<InfoPlaceVM> getViewModelClass() {
        return InfoPlaceVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.info_place_fragment, BR.viewModel, getContext());
    }
}
