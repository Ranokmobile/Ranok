package com.ranok.ui.info_position.lot_info;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranok.R;
import com.ranok.databinding.ItemLotAttributesBinding;
import com.ranok.network.models.PositionLotAttributesModel;

import java.util.ArrayList;
import java.util.List;

public class LotInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PositionLotAttributesModel> items = new ArrayList<>();

    public List<PositionLotAttributesModel> getItems() {
        return items;
    }

    public void setItems(List<PositionLotAttributesModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lot_attributes, parent, false);
        return new LotInfoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemLotAttributesBinding binding = ((LotInfoHolder)holder).binding;
        PositionLotAttributesModel model = items.get(position);
        binding.setViewModel(items.get(position));
        binding.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setExpanded(!model.isExpanded());
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class LotInfoHolder extends RecyclerView.ViewHolder {
        private ItemLotAttributesBinding binding;

        public LotInfoHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

}
