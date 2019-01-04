package com.feasycom.fsybecon.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.feasycom.controler.FscBleCentralApi;
import com.feasycom.controler.FscBleCentralApiImp;
import com.feasycom.controler.FscSppApi;
import com.feasycom.controler.FscSppApiImp;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Utils.SettingConfigUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class FilterDeviceActivity extends BaseActivity {
    @BindView(R.id.header_left)
    TextView headerLeft;
    @BindView(R.id.Search_Button)
    ImageView SearchButton;
    @BindView(R.id.Set_Button)
    ImageView SetButton;
    @BindView(R.id.About_Button)
    ImageView AboutButton;

    @BindView(R.id.filter_switch)
    Switch filterSwitch;
    @BindView(R.id.min_rssi_text)
    TextView minRssiText;
    @BindView(R.id.rssi_value_text)
    TextView rssiValueText;
    public static SeekBar rssiSeekBar;

    private FscBleCentralApi fscBleCentralApi;
    private FscSppApi fscSppApi;
    public static int filterValue = -100;
    public static boolean isFilterEnable = false;

    private Activity activity;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FilterDeviceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_device);
        activity = this;
        ButterKnife.bind(activity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        filterSwitch.setChecked((boolean) SettingConfigUtil.getData(getApplicationContext(), "filter_switch", false));
        rssiSeekBar.setProgress((int) SettingConfigUtil.getData(getApplicationContext(), "filter_value", -100));
    }

    @Override
    public void initView() {

    }

    @Override
    public void onStart(){

        super.onStart();

        activity = this;

        rssiSeekBar = findViewById(R.id.rssi_seek_bar);

        rssiSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//监听进度条
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rssiValueText.setText( ""+String.valueOf(progress-100) + " dB");
                filterValue = progress - 100;//让进度条初始值为-100

                SettingConfigUtil.saveData(getApplicationContext(), "filter_value", filterValue+100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int start = seekBar.getProgress();
                rssiValueText.setText(String.valueOf(start-100) + " dB");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int end = seekBar.getProgress();
                rssiValueText.setText(String.valueOf(end-100) + " dB");
            }
        });
    }
    @Override
    public void refreshHeader() {
        headerLeft.setText(getResources().getString(R.string.back));
    }

    public void refreshFooter() {
        SetButton.setVisibility(GONE);
        AboutButton.setVisibility(GONE);
        SearchButton.setVisibility(GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            MainActivity.actionStart(activity);
            finishActivity();
            SettingConfigUtil.saveData(getApplicationContext(), "filter_value", filterValue+100);
        }
        return true;
    }

    @OnClick(R.id.header_left)
    public void goBack() {
        MainActivity.actionStart(activity);
        finishActivity();
    }

    /*监听过滤开关*/
    @OnCheckedChanged(R.id.filter_switch)
    public void rssiSwitch(CompoundButton v, boolean flag) {
        if(flag) {
            Log.i("switch", "rssiSwitch: 1");
            isFilterEnable = true;

        }
        else{
            Log.i("switch", "rssiSwitch: 0");
            isFilterEnable = false;
        }
        SettingConfigUtil.saveData(getApplicationContext(), "filter_switch", flag);
    }
    @OnClick(R.id.Set_Button)
    @Override
    public void setClick() {
        SetActivity.actionStart(activity);
        activity.finish();
    }

    @OnClick(R.id.About_Button)
    @Override
    public void aboutClick() {
    }

    @OnClick(R.id.Search_Button)
    @Override
    public void searchClick() {
        MainActivity.actionStart(activity);
        activity.finish();
    }
/*
    @OnClick(R.id.more)
    public void onViewClicked() {
        new AboutUsDialog(activity).show();
    }
    */
}

