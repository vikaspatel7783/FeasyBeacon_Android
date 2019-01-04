package com.feasycom.fsybecon.Widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.feasycom.fsybecon.Bean.BaseEvent;
import com.feasycom.fsybecon.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */
public class PinDialog extends BaseDialog {


    private final Context context;
    @BindView(R.id.input_pin)
    EditText inputPin;
    @BindView(R.id.no)
    Button no;
    @BindView(R.id.yes)
    Button yes;

    private int position;
    private String pinString;
    private View view;

    public PinDialog(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        view = getLayoutInflater().inflate(R.layout.dialog_pin_input, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addContentView(view, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        yes.setEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @OnTextChanged(R.id.input_pin)
    protected void inputPin(Editable s) {
        this.pinString = null;
        String value = s.toString();
        if (value.length() == 6) {
            this.pinString = value.toString();
            yes.setEnabled(true);
        } else {
            yes.setEnabled(false);
        }
    }

    @OnClick(R.id.yes)
    protected void onConfirmClick() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        super.dismiss();
        BaseEvent be = new BaseEvent(BaseEvent.PIN_EVENT);
        be.setObject("pin", pinString);
        be.setObject("position", position);
        EventBus.getDefault().post(be);
    }

    @Override
    public void show() {
        super.show();
    }




    @Override
    @OnClick(R.id.no)
    public void dismiss() {
        super.dismiss();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
