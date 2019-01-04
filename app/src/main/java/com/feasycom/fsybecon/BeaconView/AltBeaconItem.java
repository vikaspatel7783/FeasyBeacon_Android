package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.bean.AltBeacon;
import com.feasycom.fsybecon.R;
import com.feasycom.util.FileUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class AltBeaconItem extends RelativeLayout {
    @BindView(R.id.tv_altbeacon_rssi)
    TextView tvAltbeaconRssi;
    @BindView(R.id.tv_altBeacon_Manufacturer_ID)
    TextView tvAltBeaconManufacturerID;
    @BindView(R.id.tv_altBeacon_id)
    TextView tvAltBeaconId;
    @BindView(R.id.tv_altBeacon_Manufacturer_Reserved)
    TextView tvAltBeaconManufacturerReserved;

    public AltBeaconItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.search_altbeacon_info, this);
        ButterKnife.bind(view);
    }

    /**
     * displays altBeacon data
     *
     * @param altBeacon
     */
    public void setAltBeaconValue(AltBeacon altBeacon) {
        if (null != altBeacon) {
            try {
                tvAltbeaconRssi.setText(Integer.valueOf(altBeacon.getAltBeaconRssi()).toString());
            } catch (Exception e) {
                tvAltbeaconRssi.setText("null");
            }
            String temp = altBeacon.getId().toLowerCase();
            try {
                temp = temp.substring(0, 32) + " - " + temp.substring(32, temp.length());
            } catch (Exception e) {
                e.printStackTrace();
                temp = "unknow";
            }
            tvAltBeaconId.setText(temp);
            try {
                tvAltBeaconManufacturerID.setText(Integer.valueOf(FileUtil.stringToInt1(altBeacon.getManufacturerId())).toString());
            } catch (Exception e) {
                e.printStackTrace();
                tvAltBeaconManufacturerID.setText("0");
            }
            tvAltBeaconManufacturerReserved.setText(altBeacon.getReservedId().toLowerCase());
        }
    }
}
