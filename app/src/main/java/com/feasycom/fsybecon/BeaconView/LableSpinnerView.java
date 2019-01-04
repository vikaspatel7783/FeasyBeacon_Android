package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.feasycom.controler.FscBeaconApi;
import com.feasycom.controler.FscBeaconApiImp;
import com.feasycom.fsybecon.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class LableSpinnerView extends LinearLayout {
    @BindView(R.id.parameterLabel)
    TextView parameterLabel;
    @BindView(R.id.spinner)
    Spinner spinner;
    private String powerParameter = "";
    private FscBeaconApi fscBeaconApi= FscBeaconApiImp.getInstance();

    public LableSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.lable_spinner_view, this);
        ButterKnife.bind(view);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LableEditView);
        String label = typedArray.getString(R.styleable.LableEditView_labelText);

        parameterLabel.setText(label);
        typedArray.recycle();
    }

    public void setRed() {
        parameterLabel.setTextColor(getResources().getColor(R.color.red));
    }

    public void setBlock() {
        parameterLabel.setTextColor(0xff1d1d1d);
    }

    public void spinnerInit(ArrayAdapter<String> spinnerAdapter, List<String> spinnerStringList) {
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
    }

    public void setSelect(int position) {
        spinner.setSelection(position);
    }

    public String getPowerParameter(){
        return powerParameter;
    }

    @OnItemSelected(R.id.spinner)
    public void powerSelect(View v, int id) {
        switch (id) {
            case 0:
                powerParameter = "";

                setRed();
                break;
            case 1:
                setBlock();
                powerParameter = "0";
                break;
            case 2:
                setBlock();
                powerParameter = "1";
                break;
            case 3:
                setBlock();
                powerParameter = "2";
                break;
            case 4:
                setBlock();
                powerParameter = "3";
                break;
            case 5:
                setBlock();
                powerParameter = "4";
                break;
            case 6:
                setBlock();
                powerParameter = "5";
                break;
            case 7:
                setBlock();
                powerParameter = "6";
                break;
            case 8:
                setBlock();
                powerParameter = "7";
                break;
            case 9:
                setBlock();
                powerParameter = "8";
                break;
            case 10:
                setBlock();
                powerParameter = "9";
                break;

            case 11:
                setBlock();
                powerParameter = "A";
                break;

            case 12:
                setBlock();
                powerParameter = "B";
                break;
            case 13:
                setBlock();
                powerParameter = "C";
                break;
            default:
                setRed();
                powerParameter = "";
        }
        fscBeaconApi.setTxPower(powerParameter);
    }
}