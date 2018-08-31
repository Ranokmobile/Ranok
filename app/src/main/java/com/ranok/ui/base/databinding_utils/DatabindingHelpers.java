package com.ranok.ui.base.databinding_utils;

import android.annotation.TargetApi;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.adapters.SearchViewBindingAdapter;
import android.os.Build;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.ranok.R;
import com.ranok.widgets.CustomViewLotAttr;
import com.squareup.picasso.Picasso;


public class DatabindingHelpers {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, int imageUrl) {
        if (imageUrl == 0) return;
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_exit_to_app)
                .into(view);
    }

    @BindingAdapter(value = {"android:onQueryTextSubmit", "android:onQueryTextChange"},
            requireAll = false)
    public static void setOnQueryTextListener(SearchView view, final SearchViewBindingAdapter.OnQueryTextSubmit submit,
                                              final SearchViewBindingAdapter.OnQueryTextChange change) {
            if (submit == null && change == null){
                view.setOnQueryTextListener(null);
            } else {
                view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (submit != null) {
                            return submit.onQueryTextSubmit(query);
                        } else {
                            return false;
                        }
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (change != null) {
                            return change.onQueryTextChange(newText);
                        } else {
                            return false;
                        }
                    }
                });
            }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public interface OnQueryTextSubmit {
        boolean onQueryTextSubmit(String query);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public interface OnQueryTextChange {
        boolean onQueryTextChange(String newText);
    }




}
