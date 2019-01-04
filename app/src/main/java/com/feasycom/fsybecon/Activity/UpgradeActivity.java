package com.feasycom.fsybecon.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.bean.BluetoothDeviceWrapper;
import com.feasycom.bean.CommandBean;
import com.feasycom.controler.FscSppApi;
import com.feasycom.controler.FscSppApiImp;
import com.feasycom.controler.FscSppCallbacksImp;
import com.feasycom.fsybecon.Controler.FscBeaconCallbacksImpOta;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Widget.CircleNumberProgress;
import com.feasycom.util.FeasyBeaconUtil;
import com.feasycom.util.FileUtil;
import com.feasycom.util.ToastUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class UpgradeActivity extends BaseActivity {
    @BindView(R.id.header_left)
    TextView headerLeft;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_right)
    TextView headerRight;
    @BindView(R.id.selectFile)
    Button selectFile;
    @BindView(R.id.selectFileLL)
    LinearLayout selectFileLL;
    @BindView(R.id.otaFileName)
    TextView otaFileName;
    @BindView(R.id.currentModule)
    TextView currentModule;
    @BindView(R.id.currentVersion)
    TextView currentVersion;
    @BindView(R.id.exceptModule)
    TextView exceptModule;
    @BindView(R.id.exceptVersion)
    TextView exceptVersion;
    @BindView(R.id.reset)
    CheckBox reset;
    @BindView(R.id.otaProgress)
    CircleNumberProgress otaProgress;
    @BindView(R.id.startOTA)
    Button startOTA;
    @BindView(R.id.Search_Button)
    ImageView SearchButton;
    @BindView(R.id.Set_Button)
    ImageView SetButton;
    @BindView(R.id.About_Button)
    ImageView AboutButton;
    @BindView(R.id.OTAInfoLL)
    LinearLayout OTAInfoLL;
    @BindView(R.id.resetLL)
    LinearLayout resetLL;
    @BindView(R.id.otaProgressLL)
    LinearLayout otaProgressLL;
    private Activity activity;
    private BluetoothDeviceWrapper bluetoothDeviceWrapper;
    private String currentVersionString;
    private String currentModuleNumberString;
    private String addr;
    private String encryptWay;
    private byte[] fileByte = null;
    private FscSppApi fscSppApi;
    private Handler handler;
    //BLE 密码
    String pin;

    public static void actionStart(Context context, BluetoothDeviceWrapper bluetoothDeviceWrapper, String pin) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("device", (Serializable) bluetoothDeviceWrapper);
        mBundle.putSerializable("pin", pin);
        intent.putExtras(mBundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ButterKnife.bind(this);
        activity = this;

        bluetoothDeviceWrapper = (BluetoothDeviceWrapper) getIntent().getSerializableExtra("device");
        pin = (String) getIntent().getSerializableExtra("pin");

        try {
            currentVersionString = bluetoothDeviceWrapper.getFeasyBeacon().getVersion();
            currentModuleNumberString = bluetoothDeviceWrapper.getFeasyBeacon().getModule();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("".equals(currentVersionString) || null == currentVersionString || currentVersionString.length() != 3) {
            currentVersionString = "unknow";
        }
        addr = bluetoothDeviceWrapper.getAddress();
        try {
            encryptWay = bluetoothDeviceWrapper.getFeasyBeacon().getEncryptionWay();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("".equals(getIntent().getStringExtra("pin")) || null == getIntent().getStringExtra("pin")) {
            pin = null;
        } else {
            pin = getIntent().getStringExtra("pin");
        }
        initView();
        handler = new Handler();
        OTAInfoAdapter();
        fscSppApi = FscSppApiImp.getInstance(activity);
        fscSppApi.initialize();
        fscSppApi.setCallbacks(new FscBeaconCallbacksImpOta(new WeakReference<UpgradeActivity>((UpgradeActivity) activity)));
        fscSppApi.connect(addr, pin);
    }
    Runnable reConnect=new Runnable() {
        @Override
        public void run() {
            if(!fscSppApi.isConnected()){
                fscSppApi.connect(addr, pin);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        try {
            handler.removeCallbacks(reConnect);
            handler.removeCallbacks(goBackRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fscSppApi.disconnect();
        super.onDestroy();
    }

    @Override
    public void refreshHeader() {
        headerTitle.setText(getResources().getString(R.string.update));
        headerLeft.setText(getResources().getString(R.string.back));
    }

    @Override
    public void refreshFooter() {
        //footer image src init
        SetButton.setImageResource(R.drawable.setting_on);
        AboutButton.setImageResource(R.drawable.about_off);
        SearchButton.setImageResource(R.drawable.search_off);
    }

    @Override
    public void initView() {
        // selectFileLL.setVisibility(View.VISIBLE);
        currentModule.setText(FeasyBeaconUtil.moduleAdapter(currentModuleNumberString));
        currentVersion.setText(currentVersionString);
    }

    public void OTAFinish() {
        handler.postDelayed(goBackRunnable, 3000);
    }

    private void OTAInfoAdapter() {
        String[] typeNumber = getResources().getStringArray(R.array.type_number);
        String[] dfuFile = getResources().getStringArray(R.array.dfu_file);
        String[] dfuFileName = getResources().getStringArray(R.array.dfu_file_name);
        if ((typeNumber.length == dfuFile.length) && (dfuFile.length == dfuFileName.length)) {
        } else {
//            Log.e("failed", "please check version table");
        }
        for (int i = 0; i < typeNumber.length; i++) {
            if (typeNumber[i].equals(currentModuleNumberString)) {
                fileByte = FileUtil.hexToByte(dfuFile[i]);
                otaFileName.setText(dfuFileName[i]);
                exceptModule.setText(FeasyBeaconUtil.getModelByFileName(dfuFileName[i]));
                exceptVersion.setText(FeasyBeaconUtil.getVersionByFileName(dfuFileName[i]));
            }
        }
    }


    @OnCheckedChanged(R.id.reset)
    public void restCheck(boolean check) {
    }

    

    @OnClick(R.id.startOTA)
    public void startOTA() {
        if (fscSppApi.isConnected()) {
            fscSppApi.startOTA(fileByte, reset.isChecked());
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activity,"failed device is not connected");
                    startOTA.setEnabled(false);
                }
            });
            OTAFinish();
        }
    }

    public void OTAViewSwitch(final boolean begin) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (begin) {
                    OTAInfoLL.setVisibility(View.GONE);
                    resetLL.setVisibility(View.GONE);
                    otaProgressLL.setVisibility(View.VISIBLE);
                } else {
                    OTAInfoLL.setVisibility(View.VISIBLE);
                    resetLL.setVisibility(View.VISIBLE);
                    otaProgressLL.setVisibility(View.GONE);
                }
                otaProgress.setProgress(0);
            }
        });

    }

    @OnClick(R.id.header_left)
    public void goBack() {
        fscSppApi.disconnect();
        SetActivity.actionStart(activity);
        finishActivity();
    }

    @OnClick(R.id.Set_Button)
    @Override
    public void setClick() {
        // 什么也不干
    }

    @OnClick(R.id.About_Button)
    @Override
    public void aboutClick() {
        AboutActivity.actionStart(activity);
        finishActivity();
    }

    @OnClick(R.id.Search_Button)
    @Override
    public void searchClick() {
        MainActivity.actionStart(activity);
        finishActivity();
    }

    Runnable goBackRunnable = new Runnable() {
        @Override
        public void run() {
            SetActivity.actionStart(activity);
            finishActivity();
        }
    };

    public Handler getHandler() {
        return handler;
    }

    public Button getStartOTA() {
        return startOTA;
    }

    public CircleNumberProgress getOtaProgress() {
        return otaProgress;
    }

    public String getPin() {
        return pin;
    }
}
