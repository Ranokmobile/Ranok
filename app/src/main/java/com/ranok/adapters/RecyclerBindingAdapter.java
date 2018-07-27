package com.ranok.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Sasha on 25.06.2016.
 * new RecyclerBindingAdapter<>(R.layout.item_holder, BR.data, list);
 */
public class RecyclerBindingAdapter<T>
        extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder> {
    private int holderLayout, variableId;
    protected List<T> items = new ArrayList<>();
    private OnItemClickListener<T> onItemClickListener;
    private Object callBack;

    public RecyclerBindingAdapter(int holderLayout, int variableId, List<T> items) {
        this.holderLayout = holderLayout;
        this.variableId = variableId;
        this.items = items;
    }

    public RecyclerBindingAdapter(int holderLayout, int variableId, List<T> items, Object callBack) {
        this(holderLayout, variableId, items);
        this.callBack = callBack;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public void notifyItem(T item){
        int position = items.indexOf(item);
        notifyItemChanged(position);
    }
    public void updateItem(T item) {
        int position = items.indexOf(item);
        items.set(position, item);
    }

    public void setItemsNoRefresh(List<T> items) {
        this.items = items;
    }


    public List<T> getItems() {
        return items;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerBindingAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(holderLayout, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerBindingAdapter.BindingHolder holder, int position) {
        final T item = items.get(position);
        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(position, item);
        });
       // if (callBack != null) holder.getBinding().setVariable(BR.callBack, callBack);
        holder.getBinding().setVariable(variableId, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }


        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}