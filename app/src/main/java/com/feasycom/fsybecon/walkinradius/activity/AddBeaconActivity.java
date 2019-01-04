package com.feasycom.fsybecon.walkinradius.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class AddBeaconActivity extends com.feasycom.fsybecon.Activity.BaseActivity {
    public static final String SELECTED_BEACON_MAC = "SelectedBeaconMac";
    private static final int REQUEST_CODE_ACCOUNT_BEACONS = 11;

    @BindView(R.id.header_left)
    TextView headerLeft;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_right)
    TextView headerRight;
    //@BindView(R.id.beaconType)
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
    //private List<String> beaconTypelist;
    private ArrayAdapter<String> spinnerAdapter;
    private BeaconBean beaconBean;
    private String selectedBeaconMac;
    public static final int REQUEST_BEACON_ADD_OK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beacon);
        ButterKnife.bind(this);
        activity = this;
        fscBeaconApi = FscBeaconApiImp.getInstance();
        //beaconTypelist = Arrays.asList(getResources().getStringArray(R.array.beacon_table));
        beaconBean = new BeaconBean();
        initView();
        settingParameterEddystoneUrl.setBeaconBean(beaconBean);
        settingParameterEddystoneUid.setBeaconBean(beaconBean);
        settingParameterIbeacon.setBeaconBean(beaconBean);
        settingParameterAltbeacon.setBeaconBean(beaconBean);

        Intent intentAccountBeacons = new Intent(this, AccountBeaconActivity.class);
        intentAccountBeacons.putExtra(AccountBeaconActivity.KEY_BEACON_SELECT_FLAG, true);
        intentAccountBeacons.putExtra(AccountBeaconActivity.KEY_SELECTED_BEACON_MAC, getSelectedBeaconMac());
        startActivityForResult(intentAccountBeacons, REQUEST_CODE_ACCOUNT_BEACONS);
    }

    private String getSelectedBeaconMac() {
        Bundle extras = getIntent().getExtras();
        if (null != extras && extras.containsKey(SELECTED_BEACON_MAC)) {
            return extras.getString(SELECTED_BEACON_MAC);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ACCOUNT_BEACONS:
                if (resultCode == RESULT_OK) {

                    Bundle extras = data.getExtras();
                    String location = extras.getString(AccountBeaconActivity.KEY_BEACON_LOCATION);
                    String model = extras.getString(AccountBeaconActivity.KEY_BEACON_MODEL);
                    String status = extras.getString(AccountBeaconActivity.KEY_BEACON_STATUS);
                    String url = extras.getString(AccountBeaconActivity.KEY_BEACON_TEMP_LINK);
                    String tempName = extras.getString(AccountBeaconActivity.KEY_BEACON_TEMP_NAME);
                    String uuid = extras.getString(AccountBeaconActivity.KEY_BEACON_UUID);

                    beaconBean.setUrl(url);
                    beaconBean.setEnable(status.equalsIgnoreCase("active"));

                    settingParameterEddystoneUrl.setUrl(url);
                    settingParameterEddystoneUrl.setEnable(status.equalsIgnoreCase("active"));

                    addBeacon();
                }
                break;
        }
    }

    @Override
    public void initView() {
        //spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, beaconTypelist);
        //beaconType.setAdapter(spinnerAdapter);
        beaconBean.setBeaconType("URL");
        settingParameterIbeacon.setVisibility(View.GONE);
        settingParameterEddystoneUid.setVisibility(View.GONE);
        settingParameterEddystoneUrl.setVisibility(View.VISIBLE);
        settingParameterAltbeacon.setVisibility(View.GONE);
    }

    @Override
    public void refreshHeader() {
        headerTitle.setText(getResources().getString(R.string.add_beacon_title));
        headerLeft.setText(getResources().getString(R.string.back));
        headerRight.setText(getResources().getString(R.string.add_beacon_right));
    }

    public void refreshFooter() {
        //findViewById(R.id.add_beacon_footer).setVisibility(View.GONE);
        //footer image src init
        /*SetButton.setImageResource(R.drawable.setting_on);
        AboutButton.setImageResource(R.drawable.about_off);
        SearchButton.setImageResource(R.drawable.search_off);*/
        /*SetButton.setVisibility(View.GONE);
        AboutButton.setVisibility(View.GONE);
        SearchButton.setVisibility(View.GONE);*/
    }

    @OnClick(R.id.Set_Button)
    public void setClick() {
    }

    @OnClick(R.id.About_Button)
    public void aboutClick() {
        fscBeaconApi.disconnect();
        /*com.feasycom.fsybecon.Activity.AboutActivity.actionStart(activity);
        activity.finish();*/
        startActivity(new Intent(this, AccountBeaconActivity.class));
        finishActivity();
    }

    @OnClick(R.id.Search_Button)
    public void searchClick() {
        fscBeaconApi.disconnect();
        com.feasycom.fsybecon.Activity.MainActivity.actionStart(activity);
        activity.finish();
    }

    @OnClick(R.id.header_left)
    public void goBack() {
        activity.finish();
    }

    @OnClick(R.id.header_right)
    public void add() {
        addBeacon();
    }

    private void addBeacon() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("beaconBean", beaconBean);
        intent.putExtras(bundle);
        setResult(REQUEST_BEACON_ADD_OK, intent);
        activity.finish();
    }

    /*@OnItemSelected(R.id.beaconType)
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
    }*/
}
