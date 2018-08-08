package com.ranok.ui.base.databinding_utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.ranok.R;
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
}
