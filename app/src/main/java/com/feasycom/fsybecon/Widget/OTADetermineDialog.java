package com.feasycom.fsybecon.Widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.feasycom.fsybecon.Bean.BaseEvent;
import com.feasycom.fsybecon.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class OTADetermineDialog extends BaseDialog {

    @BindView(R.id.button_no)
    Button buttonNo;
    @BindView(R.id.button_yes)
    Button buttonYes;
    private Context context;

    public OTADetermineDialog(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        View v = getLayoutInflater().inflate(R.layout.dialog_ota, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addContentView(v, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }


    @OnClick({R.id.button_no, R.id.button_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_no:
                EventBus.getDefault().post(new BaseEvent(BaseEvent.OTA_EVENT_NO));
                super.dismiss();
                break;
            case R.id.button_yes:
                EventBus.getDefault().post(new BaseEvent(BaseEvent.OTA_EVENT_YES));
                super.dismiss();
                break;
        }
    }
}
