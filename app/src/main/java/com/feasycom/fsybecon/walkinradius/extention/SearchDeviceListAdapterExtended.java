package com.feasycom.fsybecon.walkinradius.extention;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.feasycom.bean.BluetoothDeviceWrapper;
import com.feasycom.fsybecon.Adapter.SearchDeviceListAdapter;
import com.feasycom.fsybecon.walkinradius.manager.AccountBeaconManager;

public class SearchDeviceListAdapterExtended extends SearchDeviceListAdapter {

    private final Context mContext;
    private AccountBeaconManager accountBeaconManager = new AccountBeaconManager();

    public SearchDeviceListAdapterExtended(Context context, LayoutInflater Inflator) {
        super(context, Inflator);
        this.mContext = context;
    }

    @Override
    public synchronized boolean addDevice(BluetoothDeviceWrapper deviceDetail) {
        if (deviceDetail == null) {
            return false;
        }
        int i = 0;
        for (; i < mDevices.size(); i++) {
            if (deviceDetail.getAddress().equals(mDevices.get(i).getAddress())) {
                mDevices.get(i).setCompleteLocalName(deviceDetail.getCompleteLocalName());
                mDevices.get(i).setName(deviceDetail.getName());
                mDevices.get(i).setRssi(deviceDetail.getRssi());

                if (null != deviceDetail.getgBeacon()) {
                    if (deviceDetail.getAdvData().equals(mDevices.get(i).getAdvData())) {
                        return false;
                    }
                }

            }
        }
        Log.i("count",Integer.toString(i));
        if (i >= mDevices.size() &&
                // is walkInRadius beacon?
                accountBeaconManager.isAccountBeacon(deviceDetail.getAddress())) {
            mDevices.add(deviceDetail);
        }


        return false;
    }
}
