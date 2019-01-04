package com.feasycom.fsybecon.Controler;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import com.feasycom.bean.BluetoothDeviceWrapper;
import com.feasycom.controler.FscBeaconCallbacksImp;
import com.feasycom.fsybecon.Activity.MainActivity;
import com.feasycom.fsybecon.Activity.ParameterSettingActivity;
import com.feasycom.fsybecon.Activity.SetActivity;

import java.lang.ref.WeakReference;

import static com.feasycom.fsybecon.Activity.SetActivity.OPEN_TEST_MODE;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class FscBeaconCallbacksImpSet extends FscBeaconCallbacksImp {
    private WeakReference<SetActivity> weakReference;
    private boolean testDeviceFound = false;

    public FscBeaconCallbacksImpSet(WeakReference<SetActivity> weakReference) {
        this.weakReference = weakReference;
    }

    @Override
    public void blePeripheralFound(BluetoothDeviceWrapper device, int rssi, byte[] record) {
        /**
         * BLE search speed is fast,please pay attention to the life cycle of the device object ,directly use the final type here
         */
        if (OPEN_TEST_MODE) {
            if ((device.getName() != null) && device.getName().contains("2beacon")) {
                weakReference.get().getFscBeaconApi().stopScan();
                if (!testDeviceFound) {
                    testDeviceFound = true;
                } else {
                    return;
                }
                weakReference.get().getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ParameterSettingActivity.actionStart(weakReference.get(), device, "000000");
                        weakReference.get().finishActivity();
                    }
                }, 3000);
            }
        } else {
//            if ((null != device.getgBeacon()) || (null != device.getiBeacon()) || (null != device.getAltBeacon())) {
            if ((weakReference.get() != null) && (weakReference.get().getDeviceQueue().size() < 350)) {
                weakReference.get().getDeviceQueue().offer(device);
            }
//            }
        }

    }
}
