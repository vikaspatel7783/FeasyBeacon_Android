package com.feasycom.fsybecon.walkinradius.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feasycom.fsybecon.Activity.BaseActivity;
import com.feasycom.fsybecon.Activity.MainActivity;
import com.feasycom.fsybecon.Activity.SetActivity;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.walkinradius.UiUtils;
import com.feasycom.fsybecon.walkinradius.adapter.AccountBeaconsListAdapter;
import com.feasycom.fsybecon.walkinradius.manager.AccountBeaconManager;
import com.feasycom.fsybecon.walkinradius.manager.BeaconInfoException;
import com.feasycom.fsybecon.walkinradius.manager.UserCredentialManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walkinradius.beacon.networking.AndroidNetworking;
import com.walkinradius.beacon.networking.model.BeaconInfo;
import com.walkinradius.beacon.networking.retrofit.RetrofitNetworking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountBeaconActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_BEACON_SELECT_FLAG = "KEY_BEACON_SELECT_FLAG";

    public static final String KEY_BEACON_UUID = "BeaconUUID";
    public static final String KEY_BEACON_MODEL = "BeaconModel";
    public static final String KEY_BEACON_TEMP_NAME = "BeaconTempName";
    public static final String KEY_BEACON_TEMP_LINK = "BeaconTempLink";
    public static final String KEY_BEACON_LOCATION = "BeaconLocation";
    public static final String KEY_BEACON_STATUS = "BeaconStatus";
    public static final String KEY_SELECTED_BEACON_MAC = "SelectedBeaconMac";

    @BindView(R.id.Search_Button)
    ImageView SearchButton;
    @BindView(R.id.Set_Button)
    ImageView SetButton;
    @BindView(R.id.About_Button)
    ImageView AboutButton;

    @BindView(R.id.footer)
    LinearLayout footerView;


    private ProgressBar pgBarLogin;
    private RecyclerView mRecyclerView;
    private UserCredentialManager userCredentialManager = new UserCredentialManager();
    private AccountBeaconManager accountBeaconManager = new AccountBeaconManager();

    private AndroidNetworking mAndroidNetworking = new RetrofitNetworking();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkinradius_account_beacons_list);
        ButterKnife.bind(this);

        pgBarLogin = findViewById(R.id.pgBarLogin);
        findViewById(R.id.account_beacon_refersh).setOnClickListener(this);

        fetchAccountBeacons();
    }

    private void fetchAccountBeacons() {
        if (accountBeaconManager.getAccountBeaconList().size() > 0) {
            handleParsedBeaconsResponse(accountBeaconManager.getAccountBeaconList());
            return;
        }

        showProgressBar();
        mAndroidNetworking.getBeaconsInfo(callback,
                userCredentialManager.getUserName(),
                userCredentialManager.getUserStatus());
    }

    public void showProgressBar() {
        pgBarLogin.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        pgBarLogin.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        if (!isFinishing()) {
            UiUtils.getAlertDialog(this, "NO BEACONS", message, null).show();
        }
    }

    public void handleServerBeaconsResponse(String beaconsListJson) {
        List<BeaconInfo> beaconsList = new Gson().fromJson(beaconsListJson, new TypeToken<List<BeaconInfo>>(){}.getType());

        // Mock testing
        //List<BeaconInfo> beaconsList = new ArrayList<>();
        //addMockBeaconInfoToList(beaconsList);
        //addMockBeaconInfoToList(beaconsList);
        //addMockBeaconInfoToList(beaconsList);

        handleParsedBeaconsResponse(beaconsList);
    }


    private void addMockBeaconInfoToList(List<BeaconInfo> beaconsList) {
        BeaconInfo beaconInfo = new BeaconInfo();
        beaconInfo.uuid_no = "U333";
        beaconInfo.temp_link = "http://www.google.com";
        beaconInfo.status = "Disable";
        beaconInfo.location = "North side";
        beaconInfo.ibeacon_model_no = "Win4000R";
        beaconInfo.temp_name = "Beacon temp name";

        beaconsList.add(beaconInfo);
    }

    public void handleParsedBeaconsResponse(List<BeaconInfo> beaconsList) {

        if (null == beaconsList || beaconsList.size() == 0) {
            showMessage("No beacons received.");
            return;
        }

        accountBeaconManager.setAccountBeaconsList(beaconsList);
        try {
            accountBeaconManager.registerAccountMac();
        } catch (BeaconInfoException e) {
            UiUtils.getAlertDialog(this,
                    "AccountBeaconResponse",
                    e.getMessage(),
                    (dialog, which) -> finishActivity()).show();
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.beacons_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AccountBeaconsListAdapter accountBeaconsListAdapter = new AccountBeaconsListAdapter(beaconsList, getBeaconSelector(), getSelectedBeaconMac());
        mRecyclerView.setAdapter(accountBeaconsListAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        TextView totalCountView = findViewById(R.id.total_account_beacon);
        totalCountView.setText("TOTAL ACCOUNT TEMPLATES: "+accountBeaconsListAdapter.getItemCount());
    }

    private AndroidNetworking.Callback callback = new AndroidNetworking.Callback() {

        @Override
        public void onSuccess(String beaconsList) {
            hideProgressBar();
            handleServerBeaconsResponse(beaconsList);
        }

        @Override
        public void onFailure(String message) {
            hideProgressBar();
            showMessage(message);
        }
    };

    public AccountBeaconsListAdapter.BeaconSelectListener getBeaconSelector() {
        if (isBeaconSelectorFlagPresent()) {
            return new BeaconSelect();
        }
        return null;
    }

    private boolean isBeaconSelectorFlagPresent() {
        Bundle extras = getIntent().getExtras();
        return (extras != null && extras.containsKey(KEY_BEACON_SELECT_FLAG));
    }

    private String getSelectedBeaconMac() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY_SELECTED_BEACON_MAC)) {
            return extras.getString(KEY_SELECTED_BEACON_MAC);
        }
        return null;
    }

    class BeaconSelect implements AccountBeaconsListAdapter.BeaconSelectListener {

        @Override
        public void onBeaconSelected(BeaconInfo beaconInfo) {
            Intent intent = new Intent();
            intent.putExtra(KEY_BEACON_TEMP_NAME, beaconInfo.temp_name);
            intent.putExtra(KEY_BEACON_LOCATION, beaconInfo.location);
            intent.putExtra(KEY_BEACON_MODEL, beaconInfo.ibeacon_model_no);
            intent.putExtra(KEY_BEACON_STATUS, beaconInfo.status);
            intent.putExtra(KEY_BEACON_TEMP_LINK, beaconInfo.temp_link);
            intent.putExtra(KEY_BEACON_UUID, beaconInfo.uuid_no);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void refreshFooter() {
        if (isBeaconSelectorFlagPresent()) {
            footerView.setVisibility(View.GONE);
            /*SetButton.setVisibility(View.INVISIBLE);
            AboutButton.setVisibility(View.INVISIBLE);
            SearchButton.setVisibility(View.INVISIBLE);*/
        } else {
            SetButton.setImageResource(R.drawable.setting_off);
            AboutButton.setImageResource(R.drawable.about_on);
            SearchButton.setImageResource(R.drawable.search_off);
        }
    }

    @Override
    public void refreshHeader() {

    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.Set_Button)
    @Override
    public void setClick() {
        SetActivity.actionStart(this);
        finish();
    }

    @Override
    public void aboutClick() {

    }

    @OnClick(R.id.Search_Button)
    @Override
    public void searchClick() {
        MainActivity.actionStart(this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_beacon_refersh:
                accountBeaconManager.clear();
                fetchAccountBeacons();
                break;
        }
    }
}
