package com.feasycom.fsybecon.Widget;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */
public class AboutUsDialog extends BaseDialog {
    public boolean mScrolling = false;
    @BindView(R.id.dialog_info)
    TextView dialogInfo;
    private Activity activity;

    public float touchDownYtemp = 0;
    public float touchDownY = 0;


    public AboutUsDialog(Context context) {
        super((Activity) context);
        this.activity = (Activity) context;
        initUI();
    }

    private void initUI() {
        View v = getLayoutInflater().inflate(R.layout.dialog_aboutus_info, null,
                false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(v, lp);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        dialogInfo.setMovementMethod(new ScrollingMovementMethod());
        dialogInfo.setText(Html.fromHtml("<p><b>Feasycom</b> focus on the researching and developing of IoT (internet of things) products, including Bluetooth Modules ,WiFi and LoRa Modules,Bluetooth Beacon,etc. With more than 10-year experiences in the wireless connectivity, which ensure us have the capability for providing low-risk product development, reducing system integration cost and shortening product customization cycle to thousands of diverse customer worldwide.</p>\n" +
                "\n" +
                "<p>&nbsp</p>\n" +
                "<p>Feasycom’s engineering and design services include:</p>\n" +
                "\n" +
                "<p>&nbsp SDK</p>\n" +
                "<p>&nbsp APP Support</p>\n" +
                "<p>&nbsp PCB Design</p>\n" +
                "<p>&nbsp Development Board</p>\n" +
                "<p>&nbsp Firmware Development</p>\n" +
                "<p>&nbsp Depth Customization</p>\n" +
                "<p>&nbsp Certification Request</p>\n" +
                "<p>&nbsp Turn-Key Production Testing & Manufacturing</p>\n" +
                "<p>&nbsp</p>\n" +
                "<p>Our products and services mainly apply to Automotive, Point of Sale, Home Automation, Healthcare and Engineering, Banking, Computing, Vending Business, Location, Lighting and more.</p>\n" +
                "<p>&nbsp</p>\n" +
                "<p>Aiming at &quot<b><i>Make </b></i><b><i>Communication </b></i><b><i>Easy </b></i><b><i>and </b></i><b><i>Freely</b></i>&quot, Feasycom is dedicated to design and develop high-quality products, efficient services to customers, for today, and all days to come.</p>\n"
        ));
    }

    @OnClick(R.id.dialog_info)
    public void click() {
        if (mScrolling) {
            mScrolling = false;
        } else {
            super.dismiss();
        }
        touchDownYtemp = 0;
        touchDownY = 0;
    }

    @OnTouch(R.id.dialog_info)
    public boolean touch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = event.getY();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                touchDownYtemp = event.getY();
                if (touchDownYtemp - touchDownY > 15 || touchDownYtemp - touchDownY < -15) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
