package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.R;
import com.feasycom.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.feasycom.fsybecon.Activity.ParameterSettingActivity.firstEnter;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class LableEditView extends LinearLayout {
    @BindView(R.id.parameterLabel)
    TextView parameterLabel;
    @BindView(R.id.parameter)
    EditText parameter;
    private Context context;

    public LableEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.lable_edit_view, this);
        ButterKnife.bind(view);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LableEditView);
        String label = typedArray.getString(R.styleable.LableEditView_labelText);

        parameterLabel.setText(label);
        typedArray.recycle();
    }

    public void setText(String temp) {
        parameter.setText(temp);
    }

    /**
     * @param temp   text to display
     * @param enable true  can edit
     *               false can not edit
     */
    public void setText(String temp, boolean enable) {
        parameter.setText(temp);
        if (enable) {
            parameter.setTextColor(getResources().getColor(R.color.text_enable));
        } else {
            parameter.setTextColor(getResources().getColor(R.color.text_disable));
        }
        parameter.setEnabled(enable);
    }

    public String getText() {
        return parameter.getText().toString();
    }

    public void setSelection(int length) {
        parameter.setSelection(length);
    }

    public void setRed() {
        parameter.setTextColor(getResources().getColor(R.color.red));
        parameterLabel.setTextColor(getResources().getColor(R.color.red));
    }

    public void setInputType(int type) {
        parameter.setInputType(type);
    }

    public void setBlock() {
        parameter.setTextColor(0xff7b7b7b);
        parameterLabel.setTextColor(0xff1d1d1d);
    }

    public void setTextWacher(TextWatcher textWacher) {
        parameter.addTextChangedListener(textWacher);
    }

    /**
     * make sure the cursor is always on the right
     */
    @OnTouch(R.id.parameter)
    public boolean touchListener(EditText v, MotionEvent event) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
//        LogUtil.i("action",event.getAction()+"");
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
        }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
//            if (firstEnter) {
//                firstEnter = false;
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
//            }
        }

        return false;
    }

    @OnClick(R.id.parameter)
    public void clickListener(EditText v) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
    }
}