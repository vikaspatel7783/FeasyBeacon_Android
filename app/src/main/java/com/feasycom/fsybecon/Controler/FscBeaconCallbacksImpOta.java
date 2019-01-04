package com.feasycom.fsybecon.Controler;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.feasycom.bean.CommandBean;
import com.feasycom.controler.FscBeaconCallbacksImp;
import com.feasycom.controler.FscSppApi;
import com.feasycom.controler.FscSppCallbacksImp;
import com.feasycom.fsybecon.Activity.UpgradeActivity;
import com.feasycom.util.ToastUtil;

import java.lang.ref.WeakReference;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class FscBeaconCallbacksImpOta extends FscSppCallbacksImp {
    private WeakReference<UpgradeActivity> activityWeakReference;

    public FscBeaconCallbacksImpOta(WeakReference<UpgradeActivity> activityWeakReference) {
        this.activityWeakReference = activityWeakReference;
    }

    @Override
    public void otaProgressUpdate(final int percentage, int status) {
        if(activityWeakReference.get() == null){
            return;
        }
        if (status == FscSppApi.OTA_STATU_BEGIN) {
            activityWeakReference.get().getStartOTA().setEnabled(false);
            activityWeakReference.get().OTAViewSwitch(true);
        } else if (status == FscSppApi.OTA_STATU_FAILED) {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "ota failed...");
                    activityWeakReference.get().getStartOTA().setEnabled(false);
                }
            });
            activityWeakReference.get().OTAViewSwitch(false);
            activityWeakReference.get().OTAFinish();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            sppWrapper.connect(addr, pin);
//                        }
//                    }, 3000);
        } else if (status == FscSppApi.OTA_STATU_FINISH) {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "finish...");
                }
            });
            activityWeakReference.get().OTAViewSwitch(false);
            activityWeakReference.get().OTAFinish();
        } else if (status == FscSppApi.OTA_STATU_PROCESSING) {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityWeakReference.get().getOtaProgress().setProgress(percentage);
                }
            });
        }
    }

    @Override
    public void connectProgressUpdate(BluetoothDevice device, int status) {
        if(activityWeakReference.get() == null){
            return;
        }
        if (status == CommandBean.PASSWORD_CHECK) {
//            Log.i("password","check");
        } else if (status == CommandBean.PASSWORD_SUCCESSFULE) {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "password sucessful...");
                    activityWeakReference.get().getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activityWeakReference.get().getStartOTA().setEnabled(true);
                        }
                    }, 4000);
                }
            });
        } else if (status == CommandBean.PASSWORD_FAILED) {
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "password failed...");
                }
            });
            activityWeakReference.get().OTAFinish();
        }else if(status == CommandBean.PASSWORD_TIME_OUT){
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "password timeout...");
                }
            });
            activityWeakReference.get().OTAFinish();
        }
    }

    @Override
    public void sppConnected(BluetoothDevice device) {
        if(activityWeakReference.get() == null){
            return;
        }
        if(activityWeakReference.get().getPin()==null){
            activityWeakReference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(activityWeakReference.get(), "connected...");
                    activityWeakReference.get().getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activityWeakReference.get().getStartOTA().setEnabled(true);
                        }
                    }, 4000);
                }
            });
        }
    }
}
