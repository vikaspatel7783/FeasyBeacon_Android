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

public class DeleteDialog extends BaseDialog {

    @BindView(R.id.delete_no)
    Button deleteNo;
    @BindView(R.id.delete_yes)
    Button deleteYes;
    private Context context;
    private String index;

    public DeleteDialog(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        View v = getLayoutInflater().inflate(R.layout.dialog_delete, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addContentView(v, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @OnClick({R.id.delete_no, R.id.delete_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_no:
                super.dismiss();
                break;
            case R.id.delete_yes:
                EventBus.getDefault().post(new BaseEvent(index, BaseEvent.DELE_BEACON_EVENT));
                super.dismiss();
                break;
        }
    }
}
