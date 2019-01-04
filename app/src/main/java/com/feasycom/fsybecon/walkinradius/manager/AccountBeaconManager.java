package com.feasycom.fsybecon.walkinradius.manager;

import android.text.TextUtils;

import com.walkinradius.beacon.networking.model.BeaconInfo;

import java.util.ArrayList;
import java.util.List;

public class AccountBeaconManager {

    private static List<BeaconInfo> accountBeaconsList = new ArrayList<>();
    private MacManager macManager = new MacManager();

    public void setAccountBeaconsList(List<BeaconInfo> accountBeaconsList) {
        AccountBeaconManager.accountBeaconsList = accountBeaconsList;
    }

    public void registerAccountMac() throws BeaconInfoException {
        macManager.addMacAddress(AccountBeaconManager.accountBeaconsList);
    }

    public void clear() {
        accountBeaconsList.clear();
        macManager.clear();
    }

    public List<BeaconInfo> getAccountBeaconList() {
        return accountBeaconsList;
    }

    public boolean isAccountBeacon(String remoteBeaconMac) {
        return macManager.isAccountBeacon(remoteBeaconMac);
    }

    private static class MacManager {

        private static List<String> beaconMacList = new ArrayList<>();

        private void addMacAddress(List<BeaconInfo> accountBeaconsList) throws BeaconInfoException {
            beaconMacList.clear();

            for (BeaconInfo beaconInfo: accountBeaconsList) {

                if (TextUtils.isEmpty(beaconInfo.MAC)) {
                    throw new BeaconInfoException("MAC field is empty for temp_link: "+beaconInfo.temp_link);
                }

                if (!beaconMacList.contains(beaconInfo.MAC)) {
                    beaconMacList.add(beaconInfo.MAC);
                }
            }
        }

        boolean isAccountBeacon(String remoteMacAddress) {
            return beaconMacList.contains(remoteMacAddress);
        }

        void clear() {
            beaconMacList.clear();
        }
    }
}
