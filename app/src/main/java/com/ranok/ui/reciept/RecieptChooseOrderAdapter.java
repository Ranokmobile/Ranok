package com.ranok.ui.reciept;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.ranok.R;
import com.ranok.databinding.ItemRecieptBinding;
import com.ranok.network.models.RecieptListModel;

import java.util.ArrayList;
import java.util.List;

public class RecieptChooseOrderAdapter  extends RecyclerView.Adapter<RecieptChooseOrderAdapter.RecieptInfoHolder>
    implements Filterable{

    private RecieptChooseOrderVM.RecieptChooseOrderItemSelected recieptChooseOrderItemSelected;

    private ValueFilter valueFilter;

    private List<RecieptListModel> items, filteredItems;

    public List<RecieptListModel> getItems() {
        return items;
    }

    public void setItems(List<RecieptListModel> items) {
        this.items = items;
        this.filteredItems = items;
        notifyDataSetChanged();
    }

    public RecieptChooseOrderAdapter(List<RecieptListModel> items, RecieptChooseOrderVM.RecieptChooseOrderItemSelected recieptChooseOrderItemSelected) {
        this.recieptChooseOrderItemSelected = recieptChooseOrderItemSelected;
        this.items = items;
        this.filteredItems = items;
    }

    @NonNull
    @Override
    public RecieptInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reciept, parent, false);
        return new RecieptChooseOrderAdapter.RecieptInfoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecieptInfoHolder holder, int position) {
        ItemRecieptBinding binding = holder.binding;
        binding.setViewModel(filteredItems.get(position));
        binding.getRoot().setOnClickListener(view
                -> recieptChooseOrderItemSelected.onClick(filteredItems.get(position)));

    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<RecieptListModel> filterList = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    if ((items.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(items.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = items.size();
                results.values = items;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredItems = (List<RecieptListModel>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public static class RecieptInfoHolder extends RecyclerView.ViewHolder {
        private ItemRecieptBinding binding;

        public RecieptInfoHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

}
