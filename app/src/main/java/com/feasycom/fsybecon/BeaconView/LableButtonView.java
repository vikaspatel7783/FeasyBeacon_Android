package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class LableButtonView extends LinearLayout {

    @BindView(R.id.parameterLabel)
    TextView parameterLabel;
    @BindView(R.id.button)
    ToggleButton button;

    public LableButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.lable_button_view, this);
        ButterKnife.bind(view);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LableEditView);
        String label = typedArray.getString(R.styleable.LableEditView_labelText);

        parameterLabel.setText(label);
        typedArray.recycle();
    }

    public void setCheck(boolean enable) {
        if (enable) {
            button.setToggleOn();
        } else {
            button.setToggleOff();
        }
    }

    public void setOnToggleChanged(ToggleButton.OnToggleChanged onToggleChanged) {
        button.setOnToggleChanged(onToggleChanged);
    }

    public boolean isOpen() {
        return button.isToggleOn();
    }
}