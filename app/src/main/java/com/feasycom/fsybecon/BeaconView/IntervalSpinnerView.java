package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.feasycom.controler.FscBeaconApi;
import com.feasycom.controler.FscBeaconApiImp;
import com.feasycom.fsybecon.Activity.ParameterSettingActivity;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Widget.TipsDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static com.feasycom.fsybecon.Activity.ParameterSettingActivity.isModule_BP109;
/**
 * Created by younger on 2018/8/31.
 */

public class IntervalSpinnerView extends LinearLayout {
    @BindView(R.id.intervalLabel)
    TextView intervalLabel;
    @BindView(R.id.intervalSpinner)
    Spinner intervalSpinner;
    private int intervalCount = 0;
    private FscBeaconApi fscBeaconApi= FscBeaconApiImp.getInstance();
    private String intervalList[] = {"100","152","211","318","417","546","760","852","1022","1280","2000"};
    private TipsDialog tipsDialog;

    public IntervalSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.interval_spinner_view, this);
        ButterKnife.bind(view);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LableEditView);
        String label = typedArray.getString(R.styleable.LableEditView_labelText);
        intervalLabel.setText(label);
        tipsDialog = new TipsDialog(context);
        typedArray.recycle();
    }

    public void setRed() {
        intervalLabel.setTextColor(getResources().getColor(R.color.red));
    }

    public void setBlack() {
        intervalLabel.setTextColor(0xff1d1d1d);
    }

    public void spinnerInit(ArrayAdapter<String> spinnerAdapter, List<String> spinnerStringList) {
        intervalSpinner.setAdapter(spinnerAdapter);
        intervalSpinner.setSelection(0);
    }

    public void setSelect(int position) {
        intervalSpinner.setSelection(position);
    }


    @OnItemSelected(R.id.intervalSpinner)
    public void intervalSelect(View v, int id) {

        intervalCount++;

        if(id == 0) {
            setRed();
            return;
        }

        if(isModule_BP109 == true && intervalCount != 2){
            if(id >= 7 && id < 10) {
                tipsDialog.setInfo("Selecting this advertising interval may lower the broadcast distance!");
                tipsDialog.show();
            }
            else if(id >= 10){
                tipsDialog.setInfo("Selecting this advertising interval may lower the broadcast distance and the connection probability!");
                tipsDialog.show();
            }
        }
        else if(isModule_BP109 == false && intervalCount != 2 ){
            if (id >= 10) {
                tipsDialog.setInfo("Selecting this advertising interval may lower the connection probability!");
                tipsDialog.show();
            }
        }
        setBlack();
        fscBeaconApi.setBroadcastInterval(intervalList[id-1]);
    }
}