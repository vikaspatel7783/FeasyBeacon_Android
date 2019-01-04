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

public class Eddystone_UIDView extends LinearLayout {


    @BindView(R.id.beacon_index)
    TextView beaconIndex;
    @BindView(R.id.beacon_title)
    TextView beaconTitle;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.eddystone_namespaceLabel)
    TextView eddystoneNamespaceLabel;
    @BindView(R.id.eddystone_namespace)
    EditText eddystoneNamespace;
    @BindView(R.id.eddystone_instanceLabel)
    TextView eddystoneInstanceLabel;
    @BindView(R.id.eddystone_instance)
    EditText eddystoneInstance;
    @BindView(R.id.eddystone_reservedLabel)
    TextView eddystoneReservedLabel;
    @BindView(R.id.eddystone_reserved)
    EditText eddystoneReserved;
    @BindView(R.id.eddystone_powerLable)
    TextView eddystonePowerLable;
    @BindView(R.id.eddystone_power)
    EditText eddystonePower;
    @BindView(R.id.beacon_enable)
    SwitchButton beaconEnable;
    private BeaconBean mBeacon;
    private Context context;
    private DeleteDialog deleteDialog;

    public Eddystone_UIDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.eddystone_uid_parameter_setting, this);
        ButterKnife.bind(view);
    }

    public void setDeleteDialog(DeleteDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    public void setBeaconBean(BeaconBean beacon) {
        if (null == mBeacon) {
            this.mBeacon = beacon;
            eddystonePower.addTextChangedListener(new ViewUtil.PowerTextWatcher(eddystonePowerLable, eddystonePower, beacon));

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
        setIndex(beacon.getIndex());
        setTitle(beacon.getBeaconType());
        setNameSpace(beacon.getNameSpace());
        setInstance(beacon.getInstance());
        setReserved(beacon.getReserved());

        setPower(beacon.getPower());
        eddystonePower.addTextChangedListener(new ViewUtil.PowerTextWatcher(eddystonePowerLable, eddystonePower, beacon));

        setEnable(beacon.isEnable());

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

    public void setTitle(String type) {
        beaconTitle.setText(type);
    }

    public void setNameSpace(String temp) {
        eddystoneNamespace.setText(temp);
    }

    public void setInstance(String temp) {
        eddystoneInstance.setText(temp);
    }

    public void setReserved(String temp) {
        eddystoneReserved.setText(temp);
    }

    public void setPower(String temp) {
        eddystonePower.setText(temp);
    }

    public void setEnable(boolean flag) {
        beaconEnable.setOpened(flag);
    }

    @OnClick(R.id.image)
    public void deleteBeacon() {
        deleteDialog.setIndex(mBeacon.getIndex());
        deleteDialog.show();
    }

    /**
     * 10 byte (20 bit)limit
     *
     * @param s
     */
    @OnTextChanged(R.id.eddystone_namespace)
    public void afterTextChangedNameSpace(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 20) {
            ViewUtil.setLabelEditBlock(eddystoneNamespace, eddystoneNamespaceLabel);
            mBeacon.setNameSpace(value);
        } else {
            ViewUtil.setLabelEditRed(eddystoneNamespace, eddystoneNamespaceLabel);
        }
    }

    /**
     * 6 byte(12 bit) limit
     *
     * @param s
     */
    @OnTextChanged(R.id.eddystone_instance)
    public void afterTextChangedInstance(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 12) {
            ViewUtil.setLabelEditBlock(eddystoneInstance, eddystoneInstanceLabel);
            mBeacon.setInstance(value);
        } else {
            ViewUtil.setLabelEditRed(eddystoneInstance, eddystoneInstanceLabel);
        }
    }

    /**
     * 2 byte limit
     *
     * @param s
     */
    @OnTextChanged(R.id.eddystone_reserved)
    public void afterTextChangedReserved(Editable s) {
        String value = s.toString();
        value = value.toLowerCase();
        if (value.length() == 4) {
            ViewUtil.setLabelEditBlock(eddystoneReserved, eddystoneReservedLabel);
            mBeacon.setReserved(value);
        } else {
            ViewUtil.setLabelEditRed(eddystoneReserved, eddystoneReservedLabel);
        }
    }

    /**
     * make sure the cursor is always on the right
     */
    @OnTouch({R.id.eddystone_power, R.id.eddystone_instance, R.id.eddystone_namespace, R.id.eddystone_reserved})
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

    @OnClick({R.id.eddystone_power, R.id.eddystone_instance, R.id.eddystone_namespace, R.id.eddystone_reserved})
    public void clickListener(EditText v) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
    }
}