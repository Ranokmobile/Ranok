package com.ranok.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.ranok.R;
import com.ranok.databinding.CustomViewLotBinding;


@InverseBindingMethods({
        @InverseBindingMethod(
                type = CustomViewLotAttr.class,
                attribute = "value",
                method = "getValue")
})



public class CustomViewLotAttr extends ConstraintLayout{
    private CustomViewLotBinding binding;
    private String  value = "";
    InverseBindingListener mValueAttrChangedListener;

    public CustomViewLotAttr(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomViewLotAttr, 0, 0);
        String titleText = a.getString(R.styleable.CustomViewLotAttr_titleText);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater,R.layout.custom_view_lot, this, true);
        binding.setHeader(titleText);
        binding.setValue(value);
    }

    public CustomViewLotAttr(Context context) {
        this(context, null);
    }


    public CustomViewLotBinding getBinding() {
        return binding;
    }

    public void setBinding(CustomViewLotBinding binding) {
        this.binding = binding;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        if (!value.equals(newValue)) {
            value = newValue;
//            mColor.setSaturation(saturation);
//            updateSelectedColor();
//            updateSelectorX();
            if (mValueAttrChangedListener != null) {
                mValueAttrChangedListener.onChange();
            }
        }
    }

    public void setValueAttrChanged(InverseBindingListener listener) {
        mValueAttrChangedListener = listener;
    }



}
