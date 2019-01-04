package com.feasycom.fsybecon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.feasycom.bean.BeaconBean;
import com.feasycom.bean.FeasyBeacon;
import com.feasycom.fsybecon.BeaconView.AltBeaconView;
import com.feasycom.fsybecon.BeaconView.Eddystone_UIDView;
import com.feasycom.fsybecon.BeaconView.Eddystone_URLView;
import com.feasycom.fsybecon.BeaconView.iBeaconView;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.Widget.DeleteDialog;
import com.feasycom.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class SettingBeaconParameterListAdapter extends BaseAdapter {

    private LayoutInflater mInflator;
    private Context mContext1;
    private ArrayList<BeaconBean> beacons = new ArrayList<BeaconBean>();

    public SettingBeaconParameterListAdapter(Context context, LayoutInflater Inflator) {
        super();
        mContext1 = context;
        mInflator = Inflator;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return beacons.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return beacons.get(position);
    }

    public ArrayList<BeaconBean> getAllBeacon() {
        return beacons;
    }

    public boolean updateAllBeacon(ArrayList<BeaconBean> beaconBeens) {
        if (null == beaconBeens || beaconBeens.size() != beacons.size()) {
            return false;
        }
        beacons = beaconBeens;
        return true;
    }

    public void clearList() {
        beacons.clear();
    }

    synchronized public void addBeacon(BeaconBean beacon) {
        beacons.add(beacon);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
   synchronized  public View getView(final int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.setting_beacon_parameter_info, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BeaconBean beacon = beacons.get(position);

        if (FeasyBeacon.BEACON_TYPE_IBEACON.equals(beacon.getBeaconType())) {
            viewHolder.settingParameterIbeacon.setVisibility(View.VISIBLE);
            viewHolder.settingParameterEddystoneUrl.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUid.setVisibility(View.GONE);
            viewHolder.settingParameterAltbeacon.setVisibility(View.GONE);

            viewHolder.settingParameterIbeacon.setBeaconInfo(beacon);
            viewHolder.settingParameterIbeacon.setDeleteDialog(new DeleteDialog(mContext1));

        } else if (FeasyBeacon.BEACON_TYPE_EDDYSTONE_URL.equals(beacon.getBeaconType())) {
            viewHolder.settingParameterIbeacon.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUrl.setVisibility(View.VISIBLE);
            viewHolder.settingParameterEddystoneUid.setVisibility(View.GONE);
            viewHolder.settingParameterAltbeacon.setVisibility(View.GONE);

            viewHolder.settingParameterEddystoneUrl.setBeaconInfo(beacon);
            viewHolder.settingParameterEddystoneUrl.setDeleteDialog(new DeleteDialog(mContext1));

        } else if (FeasyBeacon.BEACON_TYPE_EDDYSTONE_UID.equals(beacon.getBeaconType())) {
            viewHolder.settingParameterIbeacon.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUrl.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUid.setVisibility(View.VISIBLE);
            viewHolder.settingParameterAltbeacon.setVisibility(View.GONE);

            viewHolder.settingParameterEddystoneUid.setBeaconInfo(beacon);
            viewHolder.settingParameterEddystoneUid.setDeleteDialog(new DeleteDialog(mContext1));

        } else if (FeasyBeacon.BEACON_TYPE_ALTBEACON.equals(beacon.getBeaconType())) {
            viewHolder.settingParameterIbeacon.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUrl.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUid.setVisibility(View.GONE);
            viewHolder.settingParameterAltbeacon.setVisibility(View.VISIBLE);

            viewHolder.settingParameterAltbeacon.setBeaconInfo(beacon);
            viewHolder.settingParameterAltbeacon.setDeleteDialog(new DeleteDialog(mContext1));
        } else {
            viewHolder.settingParameterIbeacon.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUrl.setVisibility(View.GONE);
            viewHolder.settingParameterEddystoneUid.setVisibility(View.GONE);
            viewHolder.settingParameterAltbeacon.setVisibility(View.GONE);
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.setting_parameter_ibeacon)
        iBeaconView settingParameterIbeacon;
        @BindView(R.id.setting_parameter_eddystone_uid)
        Eddystone_UIDView settingParameterEddystoneUid;
        @BindView(R.id.setting_parameter_eddystone_url)
        Eddystone_URLView settingParameterEddystoneUrl;
        @BindView(R.id.setting_parameter_altbeacon)
        AltBeaconView settingParameterAltbeacon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
