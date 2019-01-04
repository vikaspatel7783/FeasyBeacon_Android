package com.feasycom.fsybecon.Widget;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.Bean.BaseEvent;
import com.feasycom.fsybecon.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by younger on 2018/9/3.
 */

public class TipsDialog extends BaseDialog {

    @BindView(R.id.tips_got)
    Button tips_got;
    @BindView(R.id.dialog_tips)
    TextView dialogTips;
    private Context context;
    private String index;
    public TipsDialog(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        View v = getLayoutInflater().inflate(R.layout.dialog_tips, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addContentView(v, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }
    public void setInfo(final String info) {
        dialogTips.setText(info);
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @OnClick(R.id.tips_got)
    public void onViewClicked(View view) {
        super.dismiss();
    }
}
