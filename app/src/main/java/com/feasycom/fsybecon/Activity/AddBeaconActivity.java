package com.feasycom.fsybecon.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.feasycom.bean.BeaconBean;
import com.feasycom.controler.FscBeaconApi;
import com.feasycom.controler.FscBeaconApiImp;
import com.feasycom.fsybecon.BeaconView.AltBeaconView;
import com.feasycom.fsybecon.BeaconView.Eddystone_UIDView;
import com.feasycom.fsybecon.BeaconView.Eddystone_URLView;
import com.feasycom.fsybecon.BeaconView.iBeaconView;
import com.feasycom.fsybecon.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class AddBeaconActivity extends BaseActivity {
    @BindView(R.id.header_left)
    TextView headerLeft;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_right)
    TextView headerRight;
    @BindView(R.id.beaconType)
    Spinner beaconType;
    @BindView(R.id.setting_parameter_ibeacon)
    iBeaconView settingParameterIbeacon;
    @BindView(R.id.setting_parameter_eddystone_uid)
    Eddystone_UIDView settingParameterEddystoneUid;
    @BindView(R.id.setting_parameter_eddystone_url)
    Eddystone_URLView settingParameterEddystoneUrl;
    @BindView(R.id.Search_Button)
    ImageView SearchButton;
    @BindView(R.id.Set_Button)
    ImageView SetButton;
    @BindView(R.id.About_Button)
    ImageView AboutButton;
    @BindView(R.id.setting_parameter_altbeacon)
    AltBeaconView settingParameterAltbeacon;
    private Activity activity;
    private FscBeaconApi fscBeaconApi;
    private List<String> beaconTypelist;
    private ArrayAdapter<String> spinnerAdapter;
    private BeaconBean beaconBean;
    public static final int REQUEST_BEACON_ADD_OK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beacon);
        ButterKnife.bind(this);
        activity = this;
        fscBeaconApi = FscBeaconApiImp.getInstance();
        beaconTypelist = Arrays.asList(getResources().getStringArray(R.array.beacon_table));
        initView();
        beaconBean = new BeaconBean();

        settingParameterEddystoneUrl.setBeaconBean(beaconBean);
        settingParameterEddystoneUid.setBeaconBean(beaconBean);
        settingParameterIbeacon.setBeaconBean(beaconBean);
        settingParameterAltbeacon.setBeaconBean(beaconBean);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, beaconTypelist);
        beaconType.setAdapter(spinnerAdapter);
    }

    @Override
    public void refreshHeader() {
        headerTitle.setText(getResources().getString(R.string.add_beacon_title));
        headerLeft.setText(getResources().getString(R.string.back));
        headerRight.setText(getResources().getString(R.string.add_beacon_right));
    }

    public void refreshFooter() {
        //footer image src init
        SetButton.setImageResource(R.drawable.setting_on);
        AboutButton.setImageResource(R.drawable.about_off);
        SearchButton.setImageResource(R.drawable.search_off);
    }

    @OnClick(R.id.Set_Button)
    public void setClick() {
    }

    @OnClick(R.id.About_Button)
    public void aboutClick() {
        fscBeaconApi.disconnect();
        AboutActivity.actionStart(activity);
        activity.finish();
    }

    @OnClick(R.id.Search_Button)
    public void searchClick() {
        fscBeaconApi.disconnect();
        MainActivity.actionStart(activity);
        activity.finish();
    }

    @OnClick(R.id.header_left)
    public void goBack() {
        activity.finish();
//        activity.overridePendingTransition(0, 0);
    }

    @OnClick(R.id.header_right)
    public void add() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("beaconBean", beaconBean);
        intent.putExtras(bundle);
        setResult(REQUEST_BEACON_ADD_OK, intent);
        activity.finish();
    }

    @OnItemSelected(R.id.beaconType)
    public void beaconSelect(View v, int id) {
        switch (id) {
            case 0:
                beaconBean.setBeaconType("");
                settingParameterIbeacon.setVisibility(View.GONE);
                settingParameterEddystoneUid.setVisibility(View.GONE);
                settingParameterEddystoneUrl.setVisibility(View.GONE);
                settingParameterAltbeacon.setVisibility(View.GONE);
                break;
            case 1: //UID
                beaconBean.setBeaconType("UID");
                settingParameterIbeacon.setVisibility(View.GONE);
                settingParameterEddystoneUid.setVisibility(View.VISIBLE);
                settingParameterEddystoneUrl.setVisibility(View.GONE);
                settingParameterAltbeacon.setVisibility(View.GONE);
                break;
            case 2: //URL
                beaconBean.setBeaconType("URL");
                settingParameterIbeacon.setVisibility(View.GONE);
                settingParameterEddystoneUid.setVisibility(View.GONE);
                settingParameterEddystoneUrl.setVisibility(View.VISIBLE);
                settingParameterAltbeacon.setVisibility(View.GONE);
                break;
            case 3: //iBeacon
                beaconBean.setBeaconType("iBeacon");
                settingParameterIbeacon.setVisibility(View.VISIBLE);
                settingParameterEddystoneUid.setVisibility(View.GONE);
                settingParameterEddystoneUrl.setVisibility(View.GONE);
                settingParameterAltbeacon.setVisibility(View.GONE);
                break;
            case 4: //AltBeacon
                beaconBean.setBeaconType("AltBeacon");
                settingParameterIbeacon.setVisibility(View.GONE);
                settingParameterEddystoneUid.setVisibility(View.GONE);
                settingParameterEddystoneUrl.setVisibility(View.GONE);
                settingParameterAltbeacon.setVisibility(View.VISIBLE);
                break;
        }
    }
}
