package com.ranok.ui.dialogs;

import com.ranok.adapters.RecyclerBindingAdapter;

import java.util.List;
import java.util.Set;

public class SelectableRecyclerAdapter<S extends SelectDialogFragment.Selectable> extends RecyclerBindingAdapter<S> {

    protected Set<Integer> selectedItems;


    public SelectableRecyclerAdapter(int holderLayout, int variableId, List<S> items, Set<Integer> selectedItems) {
        super(holderLayout, variableId, items);
        this.selectedItems = selectedItems;
    }


    @Override
    public void onBindViewHolder(RecyclerBindingAdapter.BindingHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final S item = items.get(position);
 //       holder.getBinding().setVariable(BR.isChecked, selectedItems.contains(item.getId()));
    }
}