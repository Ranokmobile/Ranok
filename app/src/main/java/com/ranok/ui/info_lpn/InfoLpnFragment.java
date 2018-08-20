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
import com.ranok.mlkit.LivePreviewActivity;
import com.ranok.network.models.LpnPositionModel;
import com.ranok.rx_bus.RxLpnOperation;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.dialogs.SelectDialogFragment;
import com.ranok.ui.info_position.SelectPositionFragment;
import com.ranok.ui.move_lpn.MoveLpnFragment;
import com.ranok.ui.split_lpn.SplitLpnFragment;
import com.ranok.utils.StringUtils;
import com.ranok.utils.Utils;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import ranok.mvvm.binding.ViewModelBindingConfig;


public class InfoLpnFragment extends BaseFragment<InfoLpnIView, InfoLpnVM, InfoLpnFragmentBinding>
        implements InfoLpnIView, TextView.OnEditorActionListener {

    private static final int REQUEST_CODE_SERVICE = 2;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        if (requestCode == REQUEST_CODE_SERVICE) {
            if (resultCode == SelectDialogFragment.RESULT_CODE) {
                int id = data.getIntExtra("ID", 0);
                int position = data.getIntExtra("POSITION", -1);
                if(position>=0) {
                    LpnPositionModel model = getViewModel().getListLpnPositions().get(position);
                    mActivity.addFragment(SplitLpnFragment.getInstance(getViewModel().getSearchVM().getInputText(), model));
                }
            }
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
        mActivity.showFragment(MoveLpnFragment.getInstance(lpn));
    }

    @Override
    public void showSplit(String lpn, ArrayList<LpnPositionModel> positions) {
        if (positions == null || positions.size()==0) return;

        if (positions.size()==1) {
            mActivity.addFragment(SplitLpnFragment.getInstance(lpn, positions.get(0)));
        } else {
            SelectPositionFragment.Builder builder = new SelectPositionFragment.Builder();
            DialogFragment fragment = builder.setSourceList(positions)
                    .setItemLayout(R.layout.item_select)
                    .setHeaderText("Выберите элемент")
                    .build(this, REQUEST_CODE_SERVICE);
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
