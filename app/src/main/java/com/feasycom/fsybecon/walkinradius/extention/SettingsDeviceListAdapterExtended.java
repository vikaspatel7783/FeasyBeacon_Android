package com.feasycom.fsybecon.walkinradius.extention;

import android.content.Context;
import android.view.LayoutInflater;

import com.feasycom.bean.BluetoothDeviceWrapper;
import com.feasycom.fsybecon.Adapter.SettingDeviceListAdapter;
import com.feasycom.fsybecon.walkinradius.manager.AccountBeaconManager;

public class SettingsDeviceListAdapterExtended extends SettingDeviceListAdapter {

    private AccountBeaconManager accountBeaconManager = new AccountBeaconManager();

    public SettingsDeviceListAdapterExtended(Context context, LayoutInflater Inflator) {
        super(context, Inflator);
    }

    @Override
    public synchronized void addDevice(BluetoothDeviceWrapper deviceDetail) {
        if (deviceDetail == null) {
            return;
        }

        if (null == deviceDetail.getFeasyBeacon()) {
            return;
        }

        if (isPreConditionsMet(deviceDetail)) {

            int i = 0;

            for (; i < mDevices.size(); i++) {

                if (deviceDetail.getAddress().equals(mDevices.get(i).getAddress())) {

                    mDevices.get(i).setCompleteLocalName(deviceDetail.getCompleteLocalName());
                    mDevices.get(i).setName(deviceDetail.getName());
                    mDevices.get(i).setRssi(deviceDetail.getRssi());

                    mDevices.get(i).setgBeacon(deviceDetail.getgBeacon());

                    break;
                }
            }
            if (i >= mDevices.size() &&
                    accountBeaconManager.isAccountBeacon(deviceDetail.getAddress())) {
                mDevices.add(deviceDetail);
            }
        }
    }

    private boolean isPreConditionsMet(BluetoothDeviceWrapper deviceDetail) {
        return ("25".equals(deviceDetail.getFeasyBeacon().getmodule()) || "26".equals(deviceDetail.getFeasyBeacon().getmodule())
                || "27".equals(deviceDetail.getFeasyBeacon().getmodule()) || "28".equals(deviceDetail.getFeasyBeacon().getmodule())
                || "29".equals(deviceDetail.getFeasyBeacon().getmodule())) && (deviceDetail.getRssi() >= -80);
    }
}
