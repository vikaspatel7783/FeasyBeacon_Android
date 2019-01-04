package com.feasycom.fsybecon.walkinradius.extention;

import com.feasycom.bean.BluetoothDeviceWrapper;
import com.feasycom.fsybecon.Activity.MainActivity;
import com.feasycom.fsybecon.Controler.FscBeaconCallbacksImpMain;

import java.lang.ref.WeakReference;

public class FscBeaconCallbacksImpMainExtended extends FscBeaconCallbacksImpMain {

    public FscBeaconCallbacksImpMainExtended(WeakReference<MainActivity> weakReference) {
        super(weakReference);
    }

    @Override
    public void blePeripheralFound(BluetoothDeviceWrapper device, int rssi, byte[] record) {
        /**
         * BLE search speed is fast,please pay attention to the life cycle of the device object ,directly use the final type here
         */
        if ((null != device.getgBeacon()) /*|| (null != device.getiBeacon()) || (null != device.getAltBeacon())*/) {
            if ((weakReference.get()!=null) && (weakReference.get().getDeviceQueue().size() < 350)) {
                weakReference.get().getDeviceQueue().offer(device);
            }
        }
    }

}
