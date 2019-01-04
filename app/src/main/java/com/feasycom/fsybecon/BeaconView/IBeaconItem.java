package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.bean.Ibeacon;
import com.feasycom.fsybecon.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class IBeaconItem extends RelativeLayout {
    @BindView(R.id.tv_ibeacon_power)
    TextView tvIbeaconPower;
    @BindView(R.id.tv_ibeacon_uuid)
    TextView tvIbeaconUuid;
    @BindView(R.id.tv_ibeacon_major)
    TextView tvIbeaconMajor;
    @BindView(R.id.tv_ibeacon_minor)
    TextView tvIbeaconMinor;

    public IBeaconItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.search_ibeacon_info, this);
        ButterKnife.bind(view);
    }

    /**
     * display ibeaconData
     * @param iBeacon
     */
    public void setIBeaconValue(Ibeacon iBeacon) {
        if (null != iBeacon) {
            String string = "";
            try {
                string = Integer.valueOf(iBeacon.getMajor()).toString();
            } catch (Exception e) {
                string = "unknow";
            }
            tvIbeaconMajor.setText(string);
            try {
                string = Integer.valueOf(iBeacon.getMinor()).toString();
            } catch (Exception e) {
                string = "unknow";
            }
            tvIbeaconMinor.setText(string);
            tvIbeaconUuid.setText(iBeacon.getUuid());
            try {
                string = Integer.valueOf(iBeacon.getiBeaconRssi()).toString();
            } catch (Exception e) {
                string = "unkown";
            }
            tvIbeaconPower.setText(string);
        }
    }
}
