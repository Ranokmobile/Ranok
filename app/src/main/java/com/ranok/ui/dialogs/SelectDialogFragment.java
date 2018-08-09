package com.ranok.ui.dialogs;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranok.R;
import com.ranok.BR;
import com.ranok.adapters.RecyclerBindingAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.ranok.ui.dialogs.DialogBuilder.HEADER_TEXT;
import static com.ranok.ui.dialogs.DialogBuilder.ITEM_LAYOUT;
import static com.ranok.ui.dialogs.DialogBuilder.SELECTED_ITEMS;
import static com.ranok.ui.dialogs.DialogBuilder.SOURCE_LIST;

public abstract class SelectDialogFragment<T extends SelectDialogFragment.Selectable> extends DialogFragment
        implements RecyclerBindingAdapter.OnItemClickListener<T> {

    @BindingAdapter("android:typeface")
    public static void setTypeface(TextView v, String style) {
        switch (style) {
            case "bold":
                v.setTypeface(null, Typeface.BOLD);
                break;
            default:
                v.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    public interface Selectable extends Parcelable {
        String getName();
        int getId();
        default int getType(){
            return 0;
        }
    }

    public abstract static class Builder extends DialogBuilder<SelectDialogFragment> { }

    public static final int RESULT_CODE = 32816;
    public SelectDialogSettings viewModel = new SelectDialogSettingsBuilder().createSelectDialogSettings();
    protected ArrayList<T> sourceList;
    protected Set<Integer> selectedItems = new HashSet<>();
    protected SelectableRecyclerAdapter<T> adapter;
    protected ViewDataBinding binding;
    protected View mRootView;
    protected int itemLayoutId = R.layout.item_select;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (savedInstanceState != null) arguments = savedInstanceState;
        if(arguments != null) {
            sourceList = arguments.getParcelableArrayList(SOURCE_LIST);

            ArrayList<Integer> al  = arguments.getIntegerArrayList(SELECTED_ITEMS);
            if (al != null) selectedItems.addAll(al);

            viewModel.setHeader(arguments.getString(HEADER_TEXT));

            itemLayoutId = arguments.getInt(ITEM_LAYOUT);
        }

        binding = DataBindingUtil.inflate(inflater, getMainLayout(), container, false);
        mRootView = binding.getRoot();
        RecyclerView rv = mRootView.findViewById(R.id.rv);
        adapter = new SelectableRecyclerAdapter<>(itemLayoutId, BR.viewModel, sourceList, selectedItems);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        DividerItemDecoration decor = new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(ContextCompat.getDrawable(inflater.getContext(),R.drawable.divider_horizontal_gray));
        rv.addItemDecoration(decor);
        binding.setVariable(BR.viewModel, viewModel);
        return mRootView;
    }

    protected abstract int getMainLayout();



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SOURCE_LIST, sourceList);
        outState.putIntegerArrayList(SELECTED_ITEMS, new ArrayList<>(selectedItems));
        outState.putInt(ITEM_LAYOUT, itemLayoutId);
        outState.putString(HEADER_TEXT, viewModel.getHeader());
    }

    @Override
    public void onItemClick(int position, T item) {
        if (selectedItems.contains(item.getId())) {
            selectedItems.remove(item.getId());
        } else {
            selectedItems.add(item.getId());
        }
        adapter.notifyItemChanged(position);
    }

}

