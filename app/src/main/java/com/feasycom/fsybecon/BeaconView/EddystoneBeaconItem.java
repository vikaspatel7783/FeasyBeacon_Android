package com.feasycom.fsybecon.BeaconView;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.bean.EddystoneBeacon;
import com.feasycom.fsybecon.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */
public class EddystoneBeaconItem extends RelativeLayout {
    public String strData;
    @BindView(R.id.tv_frame_type)
    TextView tvFrameType;
    @BindView(R.id.tv_eddystone_power)
    TextView tvEddystonePower;
    @BindView(R.id.tv_eddystone_url)
    TextView tvEddystoneUrl;
    @BindView(R.id.ll_eddystone_url)
    LinearLayout llEddystoneUrl;
    @BindView(R.id.tv_eddystone_namespace)
    TextView tvEddystoneNamespace;
    @BindView(R.id.tv_eddystone_intance)
    TextView tvEddystoneIntance;
    @BindView(R.id.tv_eddystone_reserved)
    TextView tvEddystoneReserved;
    @BindView(R.id.ll_eddystone_uid)
    LinearLayout llEddystoneUid;

    public EddystoneBeaconItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.search_eddystone_info, this);
        ButterKnife.bind(view);
    }

    /**
     * display googleBeacon data
     * @param gBeacon
     */
    public void setGBeaconValue(EddystoneBeacon gBeacon) {
        if (gBeacon != null) {
            strData = gBeacon.getDataValue();
            if ("URL".equals(gBeacon.getFrameTypeString())) {
                llEddystoneUrl.setVisibility(VISIBLE);
                llEddystoneUid.setVisibility(GONE);
                tvEddystonePower.setText(Integer.valueOf(gBeacon.getEddystoneRssi()).toString());
                tvFrameType.setText("(" + gBeacon.getFrameTypeString() + ") " + "0x" + gBeacon.getFrameTypeHex());
                tvEddystoneUrl.setText(strData);
                tvEddystoneUrl.setTextColor(getResources().getColor(R.color.text_color_blue));
                tvEddystoneUrl.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//underline
            } else {
                for (int i = strData.length(); i <= 36; i++) {
                    strData = strData + "0";
                }
                llEddystoneUrl.setVisibility(GONE);
                llEddystoneUid.setVisibility(VISIBLE);
                tvEddystonePower.setText(Integer.valueOf(gBeacon.getEddystoneRssi()).toString());
                tvFrameType.setText("(" + gBeacon.getFrameTypeString() + ") " + "0x" + gBeacon.getFrameTypeHex());
                tvEddystoneNamespace.setText(gBeacon.getNameSpace());
                tvEddystoneIntance.setText(gBeacon.getInstance());
                tvEddystoneReserved.setText(gBeacon.getReserved());
            }
        }
    }
}
