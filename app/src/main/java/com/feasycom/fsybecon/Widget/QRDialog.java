package com.feasycom.fsybecon.Widget;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.feasycom.fsybecon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class QRDialog extends BaseDialog {
    @BindView(R.id.dialog_info)
    ImageView dialogInfo;
    private Activity activity;

    public QRDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        initUI();
    }

    private void initUI() {
        View v = getLayoutInflater().inflate(R.layout.dialog_qr, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(v, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.dialog_info)
    public void onViewClicked() {
        super.dismiss();
    }
}
