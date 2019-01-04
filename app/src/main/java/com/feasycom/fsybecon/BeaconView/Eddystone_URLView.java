package com.feasycom.fsybecon.BeaconView;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

public class Eddystone_URLView extends LinearLayout {
    public static final String URL_PROTOCOL_HTTP_WWW_DOT = "http://www.";
    public static final String URL_PROTOCOL_HTTPS_WWW_DOT = "https://www.";
    public static final String URL_PROTOCOL_HTTP = "http";
    public static final String URL_PROTOCOL_HTTP_COLON_SLASH_SLASH = "http://";
    public static final String URL_PROTOCOL_HTTPS_COLON_SLASH_SLASH = "https://";
    public static final String URL_HOST_WWW = "www.";
    public static final String URL_TLD_DOT_COM = ".com";
    public static final String URL_TLD_DOT_ORG = ".org";
    public static final String URL_TLD_DOT_EDU = ".edu";
    public static final String URL_TLD_DOT_NET = ".net";
    public static final String URL_TLD_DOT_INFO = ".info";
    public static final String URL_TLD_DOT_BIZ = ".biz";
    public static final String URL_TLD_DOT_GOV = ".gov";
    public static final String URL_TLD_DOT_COM_SLASH = ".com/";
    public static final String URL_TLD_DOT_ORG_SLASH = ".org/";
    public static final String URL_TLD_DOT_EDU_SLASH = ".edu/";
    public static final String URL_TLD_DOT_NET_SLASH = ".net/";
    public static final String URL_TLD_DOT_INFO_SLASH = ".info/";
    public static final String URL_TLD_DOT_BIZ_SLASH = ".biz/";
    public static final String URL_TLD_DOT_GOV_SLASH = ".gov/";

    @BindView(R.id.beacon_index)
    TextView beaconIndex;
    @BindView(R.id.beacon_title)
    TextView beaconTitle;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.eddystone_urlLabel)
    TextView eddystoneUrlLabel;
    @BindView(R.id.eddystone_url)
    EditText eddystoneUrl;
    @BindView(R.id.eddystone_power_lable)
    TextView eddystonePowerLable;
    @BindView(R.id.eddystone_power)
    EditText eddystonePower;
    @BindView(R.id.beacon_enable)
    SwitchButton beaconEnable;
    private BeaconBean mBeacon;
    private Context context;
    private DeleteDialog deleteDialog;

    public Eddystone_URLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.eddystone_url_parameter_setting, this);
        ButterKnife.bind(view);

    }

    public void setDeleteDialog(DeleteDialog deleteDialog) {
        this.deleteDialog = deleteDialog;
    }

    public void setBeaconBean(BeaconBean beacon) {
        if (null == mBeacon) {
            this.mBeacon = beacon;
            eddystonePower.addTextChangedListener(new ViewUtil.PowerTextWatcher(eddystonePowerLable, eddystonePower, mBeacon));

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

    public void setBeaconInfo(final BeaconBean beacon) {
        mBeacon = beacon;
        setIndex(Integer.valueOf(beacon.getIndex()).toString());
        setTitle(beacon.getBeaconType());
        setUrl(beacon.getUrl());

        setPower(beacon.getPower());
        eddystonePower.addTextChangedListener(new ViewUtil.PowerTextWatcher(eddystonePowerLable, eddystonePower, mBeacon));
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

    public void setUrl(String temp) {
        eddystoneUrl.setText(temp);
    }

    public void setPower(String temp) {
        eddystonePower.setText(temp);
    }

    public void setIndex(String temp) {
        beaconIndex.setText(temp);
    }

    public void setTitle(String type) {
        beaconTitle.setText(type);
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
     * URL limit
     *
     * @param s
     */
    @OnTextChanged(R.id.eddystone_url)
    public void afterTextChangedURL(Editable s) {
        String header_url;
        String header_url_foot;
        String value = s.toString();
        try {
            if (value.contains("https://") || value.contains("https://www") || value.contains("http://www") || value.contains("http://")) {
                header_url = getHeadByHex(value);
                header_url_foot = getFootByHex(header_url);
                if (header_url.equals(header_url_foot)) {
                    /**
                     * did not find the tail,the head 1 byte (2 bit) so minus one
                     * only up to 17 bytes
                     */
                    if (header_url.length() - 1 <= 18) {
                        ViewUtil.setLabelEditBlock(eddystoneUrl, eddystoneUrlLabel);
                        mBeacon.setUrl(value);
                    } else {
                        ViewUtil.setLabelEditRed(eddystoneUrl, eddystoneUrlLabel);
                    }
                } else {
                    int len = 0;
                    header_url_foot = header_url_foot.replace("00", "0e");
                    String[] foots = header_url_foot.split("0");
                    for (int i = 1; i < foots.length; i++) {
                        foots[i] = "0" + foots[i];
                    }
                    for (int i = 1; i < foots.length; i++) {
                        /**
                         * minus the URL header or tail 1 byte (2 bit)
                         */
                        len = len + foots[i].length() - 2;
                    }
                    len = len + foots.length;
                    if (len <= 18) {
                        ViewUtil.setLabelEditBlock(eddystoneUrl, eddystoneUrlLabel);
                        mBeacon.setUrl(value);
                    } else {
                        ViewUtil.setLabelEditRed(eddystoneUrl, eddystoneUrlLabel);
                    }
                }
            } else {
                ViewUtil.setLabelEditRed(eddystoneUrl, eddystoneUrlLabel);
            }
        } catch (Exception e) {
            ViewUtil.setLabelEditRed(eddystoneUrl, eddystoneUrlLabel);
        }

    }

    /**
     * make sure the cursor is always on the right
     */
    @OnTouch({R.id.eddystone_url, R.id.eddystone_power})
    public boolean touchListener(EditText v, MotionEvent event) {
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

    @OnClick({R.id.eddystone_url, R.id.eddystone_power})
    public void clickListener(EditText v) {
        EditText e = (EditText) v;
        e.requestFocus();
        e.setSelection(e.getText().length());
    }

    /**
     * URL header information conversion
     *
     * @return 1字节(String)
     */
    public static String getHeadByHex(String url) {
        if (url.contains(URL_PROTOCOL_HTTPS_WWW_DOT)) {
            return url.replace(URL_PROTOCOL_HTTPS_WWW_DOT, "01");        //EDDYSTONE_URL_PROTOCOL_HTTPS_WWW
        } else if (url.contains(URL_PROTOCOL_HTTP_WWW_DOT)) {
            return url.replace(URL_PROTOCOL_HTTP_WWW_DOT, "00");         //EDDYSTONE_URL_PROTOCOL_HTTP_WWW
        } else if (url.contains(URL_PROTOCOL_HTTPS_COLON_SLASH_SLASH)) {
            return url.replace(URL_PROTOCOL_HTTPS_COLON_SLASH_SLASH, "03");  // EDDYSTONE_URL_PROTOCOL_HTTP_WWW
        } else if (url.contains(URL_PROTOCOL_HTTP_COLON_SLASH_SLASH)) {
            return url.replace(URL_PROTOCOL_HTTP_COLON_SLASH_SLASH, "02");   //EDDYSTONE_URL_PROTOCOL_HTTP
        }
        return url;
    }

    /**
     * URL tail information conversion
     *
     * @return 1字节(String)
     */
    public String getFootByHex(String url) {
        if (url.contains(URL_TLD_DOT_COM_SLASH)) {
            url = url.replace(URL_TLD_DOT_COM_SLASH, "00");           // EDDYSTONE_URL_COM_SLASH
        }
        if (url.contains(URL_TLD_DOT_ORG_SLASH)) {
            url = url.replace(URL_TLD_DOT_ORG_SLASH, "01");            //EDDYSTONE_URL_ORG_SLASH
        }
        if (url.contains(URL_TLD_DOT_EDU_SLASH)) {
            url = url.replace(URL_TLD_DOT_EDU_SLASH, "02");             //EDDYSTONE_URL_EDU_SLASH
        }
        if (url.contains(URL_TLD_DOT_NET_SLASH)) {
            url = url.replace(URL_TLD_DOT_NET_SLASH, "03");             //EDDYSTONE_URL_NET_SLASH
        }
        if (url.contains(URL_TLD_DOT_INFO_SLASH)) {
            url = url.replace(URL_TLD_DOT_INFO_SLASH, "04");             //EDDYSTONE_URL_INFO_SLASH
        }
        if (url.contains(URL_TLD_DOT_BIZ_SLASH)) {
            url = url.replace(URL_TLD_DOT_BIZ_SLASH, "05");                 //EDDYSTONE_URL_BIZ_SLASH
        }
        if (url.contains(URL_TLD_DOT_GOV_SLASH)) {
            url = url.replace(URL_TLD_DOT_GOV_SLASH, "06");               //EDDYSTONE_URL_GOV_SLASH
        }
        if (url.contains(URL_TLD_DOT_COM)) {
            url = url.replace(URL_TLD_DOT_COM, "07");                      //EDDYSTONE_URL_COM
        }
        if (url.contains(URL_TLD_DOT_ORG)) {
            url = url.replace(URL_TLD_DOT_ORG, "08");                       //EDDYSTONE_URL_ORG
        }
        if (url.contains(URL_TLD_DOT_EDU)) {
            url = url.replace(URL_TLD_DOT_EDU, "09");                         //EDDYSTONE_URL_EDU
        }
        if (url.contains(URL_TLD_DOT_NET)) {
            url = url.replace(URL_TLD_DOT_NET, "0a");                        //EDDYSTONE_URL_NET
        }
        if (url.contains(URL_TLD_DOT_INFO)) {
            url = url.replace(URL_TLD_DOT_INFO, "0b");                       //EDDYSTONE_URL_INFO
        }
        if (url.contains(URL_TLD_DOT_BIZ)) {
            url = url.replace(URL_TLD_DOT_BIZ, "0c");                       //EDDYSTONE_URL_BIZ
        }
        if (url.contains(URL_TLD_DOT_GOV)) {
            url = url.replace(URL_TLD_DOT_GOV, "0d");                       //EDDYSTONE_URL_GOV
        }
        return url;
    }
}