package com.ranok.ui.info_lpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.adapters.LinearLayoutManager;
import com.ranok.databinding.InfoLpnFragmentBinding;
import com.ranok.enums.Actions;
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.models.ActionModel;
import com.ranok.network.models.PlaceInfoModel;
import com.ranok.rx_bus.RxLpnOperation;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.ActionsDialog;
import com.ranok.ui.dialogs.SelectDialogFragment;
import com.ranok.ui.info_place.InfoPlaceFragment;
import com.ranok.ui.info_position.InfoPositionFragment;
import com.ranok.ui.info_position.SelectPositionFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.ui.print_lpn.PrintLpnFragment;
import com.ranok.ui.split_lpn.SplitLpnFragment;
import com.ranok.ui.unpack_lpn.UnpackLpnFragment;
import com.ranok.utils.LpnUtils;
import com.ranok.utils.StringUtils;
import com.ranok.utils.Utils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoLpnFragment extends BaseFragment<InfoLpnIView, InfoLpnVM, InfoLpnFragmentBinding>
        implements InfoLpnIView, TextView.OnEditorActionListener {

    private static final int REQUEST_CODE_SPLIT = 2, REQUEST_CODE_UNPACK = 3,
            REQUEST_CODE_PRINT = 4, ACTION_DIALOG_CODE = 5;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static InfoLpnFragment getInstance(String lpn){
        InfoLpnFragment fragment = new InfoLpnFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lpn", lpn);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getScreenTitle() {
        return "Информация о НЗ";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        RecyclerView rv = getBinding().rv;
        RecyclerView.Adapter adapter = getViewModel().getAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_horizontal_gray));
        rv.addItemDecoration(decor);
        if (adapter != null) rv.setAdapter(adapter);

        RecyclerView rvReceipt = getBinding().rvReceipt;
        RecyclerView.Adapter adapterReceipt = getViewModel().getReceiptAdapter();
        LinearLayoutManager managerReceipt = new LinearLayoutManager(mActivity);
        rvReceipt.setLayoutManager(managerReceipt);
        rvReceipt.setHasFixedSize(false);
        rvReceipt.addItemDecoration(decor);
        if (adapterReceipt != null) rvReceipt.setAdapter(adapterReceipt);

        getBinding().menuRed.setClosedOnTouchOutside(true);

        EditText et = getBinding().searchItem.etCode;
        et.post(() -> Utils.selectText(et));
        et.setOnEditorActionListener(this);
        compositeDisposable.add(
                RxLpnOperation.getInstance().getEvents()
                .filter(s -> s != null && !s.isEmpty())
                .subscribe(this::gotNewLpn)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().startSearch();
    }

    @Override
    public void showMenu(PlaceInfoModel item) {
        ArrayList<ActionModel> list = new ArrayList<>();
        if (LpnUtils.isPositionInfoEnabled(item)) list.add(new ActionModel(Actions.POSITION_INFO));
        if (LpnUtils.isPlaceInfoEnabled(item)) list.add(new ActionModel(Actions.PLACE_INFO));
        String header = (item.getLpn() == null || item.getLpn().isEmpty()) ? item.getItemCode() : item.getLpn();
        ActionsDialog actionsDialog = ActionsDialog.getInstance(list, header, item);
        actionsDialog.setTargetFragment(this, ACTION_DIALOG_CODE);
        actionsDialog.show(mActivity.getSupportFragmentManager(), "DLG");
    }

    private void processAction(int id, PlaceInfoModel item) {
        Actions action = Actions.getById(id);
        switch (action) {
            case PLACE_INFO: mActivity.addFragment(InfoPlaceFragment.getInstance(item.getAddress()));
                break;
            case POSITION_INFO: mActivity.addFragment(InfoPositionFragment.getInstance(item.getItemCode()));
                break;
        }
    }

    @Override
    public void refreshFab() {
        getBinding().fabSplit.setEnabled(getViewModel().isFabItemVisible());
        getBinding().fabUnPack.setEnabled(getViewModel().isFabItemVisible()
                && getViewModel().getLpnInfoModel().getMayUnpack()==1);
        getBinding().fabMove.setEnabled(getViewModel().isMoveEnabled());

    }

    private void gotNewLpn(String s) {
        RxLpnOperation.getInstance().sendLpnData("");
        Utils.copyToClipboard(mActivity, StringUtils.formatFromLpn(s));
        final Snackbar snackbar = Snackbar.make(getBinding().getRoot(), "Новый НЗ: " + s, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Закрыть", view -> snackbar.dismiss());
        snackbar.show();
        getBinding().searchItem.etCode.post(()->mActivity.hideKeyboard());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) compositeDisposable.dispose();
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
        if (requestCode == REQUEST_CODE_SPLIT) {
            if (resultCode == SelectDialogFragment.RESULT_CODE) {
                int position = data.getIntExtra("POSITION", -1);
                if(position>=0) {
                    PlaceInfoModel model = getViewModel().getListLpnPositions().get(position);
                    mActivity.addFragment(SplitLpnFragment.getInstance(model));
                }
            }
        }
        if (requestCode == REQUEST_CODE_UNPACK) {
            if (resultCode == SelectDialogFragment.RESULT_CODE) {
                int position = data.getIntExtra("POSITION", -1);
                if(position>=0) {
                    PlaceInfoModel model = getViewModel().getListLpnPositions().get(position);
                    mActivity.addFragment(UnpackLpnFragment.getInstance(model));
                }
            }
        }
        if (requestCode == REQUEST_CODE_PRINT) {
            if (resultCode == SelectDialogFragment.RESULT_CODE) {
                int position = data.getIntExtra("POSITION", -1);
                if(position>=0) {
                    PlaceInfoModel model = getViewModel().getListLpnPositions().get(position);
                    mActivity.addFragment(UnpackLpnFragment.getInstance(model));
                }
            }
        }

        if (requestCode == ACTION_DIALOG_CODE && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra("id", 0);
            String header = data.getStringExtra("HEADER");
            PlaceInfoModel item = data.getParcelableExtra("ITEM");
            processAction(id, item);
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

    @Override
    public void showMove(String lpn) {
        boolean delivery = getViewModel().getLpnInfoModel().getLpnContext().equals("На приёмке");
        mActivity.addFragment(MoveLpnFragment.getInstance(lpn, delivery));
    }

    @Override
    public void showSplit(String lpn, ArrayList<PlaceInfoModel> positions) {
        if (positions == null || positions.size()==0) return;

        if (positions.size()==1) {
            mActivity.addFragment(SplitLpnFragment.getInstance(positions.get(0)));
        } else {
            SelectPositionFragment.Builder builder = new SelectPositionFragment.Builder();
            DialogFragment fragment = builder.setSourceList(positions)
                    .setItemLayout(R.layout.item_select)
                    .setHeaderText("Выберите элемент")
                    .build(this, REQUEST_CODE_SPLIT);
            fragment.show(mActivity.getSupportFragmentManager(), "DIALOG");
        }
    }

    @Override
    public void showUnpack(String lpn, ArrayList<PlaceInfoModel> positions) {
        if (positions == null || positions.size()==0) return;

        if (positions.size()==1) {
            mActivity.addFragment(UnpackLpnFragment.getInstance(positions.get(0)));
        } else {
            SelectPositionFragment.Builder builder = new SelectPositionFragment.Builder();
            DialogFragment fragment = builder.setSourceList(positions)
                    .setItemLayout(R.layout.item_select)
                    .setHeaderText("Выберите элемент")
                    .build(this, REQUEST_CODE_UNPACK);
            fragment.show(mActivity.getSupportFragmentManager(), "DIALOG");
        }
    }

    @Override
    public void showPrint(String lpn, ArrayList<PlaceInfoModel> positions) {
        if (positions == null || positions.size()==0) {
            mActivity.addFragment(PrintLpnFragment.getInstance(lpn));
        }else if (positions.size()==1) {
            mActivity.addFragment(PrintLpnFragment.getInstance(positions.get(0)));
        } else {
            SelectPositionFragment.Builder builder = new SelectPositionFragment.Builder();
            DialogFragment fragment = builder.setSourceList(positions)
                    .setItemLayout(R.layout.item_select)
                    .setHeaderText("Выберите элемент")
                    .build(this, REQUEST_CODE_PRINT);
            fragment.show(mActivity.getSupportFragmentManager(), "DIALOG");
        }
    }

    @Nullable
    @Override
    public Class<InfoLpnVM> getViewModelClass() {
        return InfoLpnVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.info_lpn_fragment, BR.viewModel, getContext());
    }
}
