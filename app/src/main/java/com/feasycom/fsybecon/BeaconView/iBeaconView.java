package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.bean.BeaconBean;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Utils.ViewUtil;
import com.feasycom.fsybecon.Widget.DeleteDialog;
import com.feasycom.fsybecon.Widget.SwitchButton;
import com.feasycom.fsybecon.Widget.ToggleButton;
import com.feasycom.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

import static com.feasycom.fsybecon.Activity.ParameterSettingActivity.firstEnter;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class iBeaconView extends LinearLayout {

    @BindView(R.id.beacon_index)
    TextView beaconIndex;
    @BindView(R.id.beacon_title)
    TextView beaconTitle;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.ibeacon_uuid_label)
    TextView ibeaconUuidLabel;
    @BindView(R.id.ibeacon_uuid)
    EditText ibeaconUuid;
    @BindView(R.id.ibeacon_major_label)
    TextView ibeaconMajorLabel;
    @BindView(R.id.ibeacon_major)
    EditText ibeaconMajor;
    @BindView(R.id.ibeacon_minor_label)
    TextView ibeaconMinorLabel;
    @BindView(R.id.ibeacon_minor)
    EditText ibeaconMinor;
    @BindView(R.id.ibeacon_power_label)
    TextView ibeaconPowerLabel;
    @BindView(R.id.ibeacon_power)
    EditText ibeaconPower;
    @BindView(R.id.beacon_enable)
    SwitchButton beaconEnable;
    private BeaconBean mBeacon;
    private DeleteDialog deleteDialog;
    private Context context;
    public iBeaconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        View view = View.inflate(context, R.layout.ibeacon_parameter_setting, this);
        ButterKnife.bind(view);
    }

    public void setDeleteDialog(DeleteDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    public void setBeaconBean(BeaconBean beacon) {
        if (null == mBeacon) {
            this.mBeacon = beacon;
            ibeaconPower.addTextChangedListener(new ViewUtil.PowerTextWatcher(ibeaconPowerLabel, ibeaconPower, mBeacon));

            beaconEnable.toggleSwitch(true);
            mBeacon.setEnable(true);

            beaconEnable.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {

                @Override
                public void toggleToOn(SwitchButton view) {
                    view.toggleSwitch(true);
                    mBeacon.setEnable(true);
                }

                @Override
                public void toggleToOff(SwitchButton view) {
                    view.toggleSwitch(false);
                    mBeacon.setEnable(false);
                }
            });
        }
    }

    public void setBeaconInfo(BeaconBean beacon) {
        mBeacon = beacon;
        beaconIndex.setText(beacon.getIndex());
        beaconTitle.setText(beacon.getBeaconType());
        ibeaconUuid.setText(beacon.getUuid());
        ibeaconMajor.setText(beacon.getMajor());
        ibeaconMinor.setText(beacon.getMinor());

        ibeaconPower.setText(beacon.getPower());
        ibeaconPower.addTextChangedListener(new ViewUtil.PowerTextWatcher(ibeaconPowerLabel, ibeaconPower, mBeacon));

        beaconEnable.setOpened(beacon.isEnable());

        beaconEnable.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {

            @Override
            public void toggleToOn(SwitchButton view) {
                mBeacon.setEnable(true);
                view.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchButton view) {
                mBeacon.setEnable(false);
                view.toggleSwitch(false);
            }
        });
        image.setImageResource(R.drawable.x);
    }

    public void setIndex(String temp) {
        beaconIndex.setText(temp);
    }

    public void setTitle() {
        beaconTitle.setText("iBeacon");
    }

    public void setUuid(String temp) {
        ibeaconUuid.setText(temp);
    }

    public void setMajor(String temp) {
        ibeaconMajor.setText(temp);
    }

    public void setMinor(String temp) {
        ibeaconMinor.setText(temp);
    }

    public void setTxpower(String temp) {
        ibeaconPower.setText(temp);
    }


    @OnClick(R.id.image)
    public void deleteBeacon() {
        deleteDialog.setIndex(mBeacon.getIndex());
        deleteDialog.show();
    }

    /**
     * major limit [0,65535]
     */
    @OnTextChanged(R.id.ibeacon_major)
    public void afterTextChangedMajor(Editable s) {
        String value = s.toString();
        if (value.length() > 0 && value.length()<=5 && Integer.valueOf(value).intValue() <= 65535) {
            ViewUtil.setLabelEditBlock(ibeaconMajor, ibeaconMajorLabel);
            mBeacon.setMajor(value);
        } else {
            ViewUtil.setLabelEditRed(ibeaconMajor, ibeaconMajorLabel);
        }
    }

    /**
     * minor limit [0,65535]
     */
    @OnTextChanged(R.id.ibeacon_minor)
    public void afterTextChangedMinor(Editable s) {
        String value = s.toString();
        if (value.length() > 0 && value.length() <=5 && Integer.valueOf(value).intValue() <= 65535) {
            ViewUtil.setLabelEditBlock(ibeaconMinor, ibeaconMinorLabel);
            mBeacon.setMinor(value);
        } else {
            ViewUtil.setLabelEditRed(ibeaconMinor, ibeaconMinorLabel);
        }
    }

    /**
     * uuid limit 0123456789abcdef , 32 bit
     *
     * @param s
     */
    @OnTextChanged(R.id.ibeacon_uuid)
    public void afterTextChangedUUID(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 32) {
            ViewUtil.setLabelEditBlock(ibeaconUuid, ibeaconUuidLabel);
            mBeacon.setUuid(value);
        } else {
            ViewUtil.setLabelEditRed(ibeaconUuid, ibeaconUuidLabel);
        }
    }

    /**
     * make sure the cursor is always on the right
     */
    @OnTouch({R.id.ibeacon_major, R.id.ibeacon_minor, R.id.ibeacon_uuid, R.id.ibeacon_power})
    public boolean touchListener(EditText v,MotionEvent event) {
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

    @OnClick({R.id.ibeacon_major, R.id.ibeacon_minor, R.id.ibeacon_uuid, R.id.ibeacon_power})
    public void clickListener(EditText v) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
    }
}