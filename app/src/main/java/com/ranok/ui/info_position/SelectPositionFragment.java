package com.ranok.ui.info_position;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.ui.base.BaseActivity;
import com.ranok.ui.dialogs.DialogBuilder;
import com.ranok.ui.dialogs.MyDiffUtilsCallback;
import com.ranok.ui.dialogs.RxSearchObservable;
import com.ranok.ui.dialogs.SelectDialogFragment;
import com.ranok.ui.dialogs.SelectableRecyclerAdapter;
import com.ranok.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.ranok.ui.dialogs.DialogBuilder.HEADER_TEXT;
import static com.ranok.ui.dialogs.DialogBuilder.ITEM_LAYOUT;
import static com.ranok.ui.dialogs.DialogBuilder.SELECTED_ITEMS;
import static com.ranok.ui.dialogs.DialogBuilder.SOURCE_LIST;

public class SelectPositionFragment <T extends SelectDialogFragment.Selectable> extends SelectDialogFragment<T>
        implements Filterable {

    public static class Builder extends DialogBuilder<SelectDialogFragment> {
        @Override
        public SelectDialogFragment getInstance() {
            return new SelectPositionFragment();
        }
    }

    public interface FilterListProvider<T> {
        List<T> getResults(CharSequence constraint);
    }

    private SearchView searchView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        try {
            searchView = mRootView.findViewById(R.id.searchView);
            initSearchView(searchView);
        } catch (Exception e) {
            e.printStackTrace();
        }

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



    private void initSearchView(SearchView searchView) {
        searchView.setOnCloseListener(() -> {
            return true; //returning true will stop search view from collapsing
        });
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        EditText textView = searchView.findViewById(id);
        textView.setHintTextColor(getResources().getColor(R.color.light_gray));
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setTextSize(18f);

        compositeDisposable.add(RxSearchObservable.fromView(searchView)
                .debounce(50, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> getFilter().filter(result))
        );
        searchView.setActivated(true);
        searchView.setIconifiedByDefault(false);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (FilterListProvider.class.isAssignableFrom( getTargetFragment().getClass() )) {
                    @SuppressWarnings(value = "unchecked")
                    FilterListProvider<T> provider = (FilterListProvider<T>)getTargetFragment();
                    List<T> results = provider.getResults(constraint);
                    filterResults.values = results;
                    filterResults.count = results.size();
                } else {
                    if (constraint == null || constraint.length() == 0) {
                        filterResults.values = sourceList;
                        filterResults.count = sourceList.size();
                    } else {
                        compositeDisposable.add(Observable.fromIterable(sourceList)
                                .filter(e -> e.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                                .toList()
                                .subscribe(ts -> {
                                    filterResults.values = ts;
                                    filterResults.count = ts.size();
                                }));
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                @SuppressWarnings(value = "unchecked")
                List<T> res = (List<T>) results.values;
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilsCallback<>(res, adapter.getItems()), true);
                adapter.setItems(res);
                diffResult.dispatchUpdatesTo(adapter);
            }
        };
    }

    @Override
    public void onItemClick(int position, T item) {
        ((BaseActivity)getActivity()).hideKeyboard();
        Intent intent = new Intent();
        intent.putExtra("ID",item.getId());
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE, intent);
        getDialog().dismiss();
    }


    @Override
    protected int getMainLayout() {
         return R.layout.select_position_fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.dismissDisposable( compositeDisposable);
    }
}
