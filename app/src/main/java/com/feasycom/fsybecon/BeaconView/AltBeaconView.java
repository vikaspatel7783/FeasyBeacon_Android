package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
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

public class AltBeaconView extends LinearLayout {
    @BindView(R.id.beacon_index)
    TextView beaconIndex;
    @BindView(R.id.beacon_title)
    TextView beaconTitle;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.altBeacon_uuid_label)
    TextView altBeaconUuidLabel;
    @BindView(R.id.altBeacon_id_1)
    EditText altBeaconID1;
    @BindView(R.id.altBeacon_major_label)
    TextView altBeaconMajorLabel;
    @BindView(R.id.altBeacon_id_2)
    EditText altBeaconID2;
    @BindView(R.id.altBeacon_minor_label)
    TextView altBeaconMinorLabel;
    @BindView(R.id.altBeacon_id_3)
    EditText altBeaconID3;
    @BindView(R.id.altBeacon_power_label)
    TextView altBeaconPowerLabel;
    @BindView(R.id.altBeacon_power)
    EditText altBeaconPower;
    @BindView(R.id.altBeacon_manufacture_id_label)
    TextView altBeaconManufactureIdLabel;
    @BindView(R.id.altBeacon_manufacture_id)
    EditText altBeaconManufactureId;
    @BindView(R.id.altBeacon_manufacture_reserved_label)
    TextView altBeaconManufactureReservedLabel;
    @BindView(R.id.altBeacon_manufacture_reserved)
    EditText altBeaconManufactureReserved;
    @BindView(R.id.beacon_enable)
    SwitchButton beaconEnable;
    private BeaconBean mBeacon;
    private DeleteDialog deleteDialog;
    private Context context;

    public AltBeaconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        View view = View.inflate(context, R.layout.altbeacon_parameter_setting, this);
        ButterKnife.bind(view);
    }

    public void setDeleteDialog(DeleteDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    public void setBeaconBean(BeaconBean beacon) {
        if (null == mBeacon) {
            this.mBeacon = beacon;
            altBeaconPower.addTextChangedListener(new ViewUtil.PowerTextWatcher(altBeaconPowerLabel, altBeaconPower, mBeacon));

            beaconEnable.toggleSwitch(true);
            mBeacon.setEnable(true);

            beaconEnable.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {

                @Override
                public void toggleToOn(SwitchButton view) {
//                    LogUtil.i("toggle","on");
                    view.toggleSwitch(true);
                    mBeacon.setEnable(true);
                }

                @Override
                public void toggleToOff(SwitchButton view) {
//                    LogUtil.i("toggle","off");
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
        altBeaconID1.setText(beacon.getId1());
        altBeaconID2.setText(beacon.getId2());
        altBeaconID3.setText(beacon.getId3());
        altBeaconManufactureReserved.setText(beacon.getManufacturerReserved());
        altBeaconManufactureId.setText(beacon.getManufacturerId());

        altBeaconPower.setText(beacon.getPower());
        altBeaconPower.addTextChangedListener(new ViewUtil.PowerTextWatcher(altBeaconPowerLabel, altBeaconPower, mBeacon));

        beaconEnable.setOpened(beacon.isEnable());
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
        image.setImageResource(R.drawable.x);
    }

    public void setIndex(String temp) {
        beaconIndex.setText(temp);
    }

    public void setTitle() {
        beaconTitle.setText("iBeacon");
    }

    public void setUuid(String temp) {
        altBeaconID1.setText(temp);
    }

    public void setMajor(String temp) {
        altBeaconID2.setText(temp);
    }

    public void setMinor(String temp) {
        altBeaconID3.setText(temp);
    }

    public void setTxpower(String temp) {
        altBeaconPower.setText(temp);
    }


    @OnClick(R.id.image)
    public void deleteBeacon() {
        deleteDialog.setIndex(mBeacon.getIndex());
        deleteDialog.show();
    }

    /**
     * major limit [0,65535]
     */
    @OnTextChanged(R.id.altBeacon_id_2)
    public void afterTextChangedMajor(Editable s) {
        String value = s.toString().toLowerCase();
        if (value.length()==4) {
            ViewUtil.setLabelEditBlock(altBeaconID2, altBeaconMajorLabel);
            mBeacon.setId2(value);
        } else {
            ViewUtil.setLabelEditRed(altBeaconID2, altBeaconMajorLabel);
        }
    }

    /**
     * minor limit [0,65535]
     */
    @OnTextChanged(R.id.altBeacon_id_3)
    public void afterTextChangedMinor(Editable s) {
        String value = s.toString().toLowerCase();
        if (value.length()==4) {
            ViewUtil.setLabelEditBlock(altBeaconID3, altBeaconMinorLabel);
            mBeacon.setId3(value);
        } else {
            ViewUtil.setLabelEditRed(altBeaconID3, altBeaconMinorLabel);
        }
    }

    /**
     * uuid limit 0123456789abcdef , 32 bit
     *
     * @param s
     */
    @OnTextChanged(R.id.altBeacon_id_1)
    public void afterTextChangedUUID(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 32) {
            ViewUtil.setLabelEditBlock(altBeaconID1, altBeaconUuidLabel);
            mBeacon.setId1(value);
        } else {
            ViewUtil.setLabelEditRed(altBeaconID1, altBeaconUuidLabel);
        }
    }
    /**
     * manufacturer id limit 0-9  decimal
     * @param s
     */
    @OnTextChanged(R.id.altBeacon_manufacture_id)
    public void afterTextChangeManufactureId(Editable s){
        String value = s.toString().toLowerCase();
        if (value.length() > 0 && value.length() <= 5 && Integer.valueOf(value).intValue() <= 65535) {
            ViewUtil.setLabelEditBlock(altBeaconManufactureId, altBeaconManufactureIdLabel);
            mBeacon.setManufacturerId(value);
        } else {
            ViewUtil.setLabelEditRed(altBeaconManufactureId, altBeaconManufactureIdLabel);
        }
    }

    /**
     * manufacturer id limit 0-9 a-f A-F  Hexadecimal 1 Byte
     *
     * @param s
     */
    @OnTextChanged(R.id.altBeacon_manufacture_reserved)
    public void afterTextChangedManufacturerReserved(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 2) {
            ViewUtil.setLabelEditBlock(altBeaconManufactureReserved, altBeaconManufactureReservedLabel);
            mBeacon.setManufacturerReserved(value);
        } else {
            ViewUtil.setLabelEditRed(altBeaconManufactureReserved, altBeaconManufactureReservedLabel);
        }
    }

    /**
     * make sure the cursor is always on the right
     */
    @OnTouch({R.id.altBeacon_id_1, R.id.altBeacon_id_2, R.id.altBeacon_id_3, R.id.altBeacon_power,R.id.altBeacon_manufacture_reserved,R.id.altBeacon_manufacture_id})
    public boolean touchListener(EditText v, MotionEvent event) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
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

    @OnClick({R.id.altBeacon_id_1, R.id.altBeacon_id_2, R.id.altBeacon_id_3, R.id.altBeacon_power,R.id.altBeacon_manufacture_reserved,R.id.altBeacon_manufacture_id})
    public void clickListener(EditText v) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
    }
}