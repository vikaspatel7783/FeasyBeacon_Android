package com.feasycom.fsybecon.walkinradius.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.R;
import com.walkinradius.beacon.networking.model.BeaconInfo;

import java.util.ArrayList;
import java.util.List;

public class AccountBeaconsListAdapter extends RecyclerView.Adapter<AccountBeaconsListAdapter.ViewHolder> {

    private BeaconSelectListener mBeaconSelectListener;
    private List<BeaconInfo> mBeaconsInfo;

    public AccountBeaconsListAdapter(List<BeaconInfo> beaconsInfo, BeaconSelectListener beaconSelectListener, String selectedBeaconMac) {
        mBeaconsInfo = beaconsInfo;
        this.mBeaconSelectListener = beaconSelectListener;

        filterSelectedBeaconMacTemplates(selectedBeaconMac);
    }

    private void filterSelectedBeaconMacTemplates(String selectedBeaconMac) {
        if (TextUtils.isEmpty(selectedBeaconMac)) {
            return;
        }

        List<BeaconInfo> filteredBeaconInfo = new ArrayList<>();
        for (BeaconInfo beaconInfo: mBeaconsInfo) {
            if (beaconInfo.MAC.equalsIgnoreCase(selectedBeaconMac)) {
                filteredBeaconInfo.add(beaconInfo);
            }
        }
        this.mBeaconsInfo = filteredBeaconInfo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //TextView mTextViewBeaconUUID;
        TextView mTextViewBeaconModel;
        TextView mTextViewBeaconTempName;
        TextView mTextViewBeaconTempLink;
        TextView mTextViewBeaconLocation;
        TextView mTextViewBeaconStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            //mTextViewBeaconUUID = itemView.findViewById(R.id.textView_uuid_value);
            mTextViewBeaconModel = itemView.findViewById(R.id.textView_model_value);
            mTextViewBeaconTempName = itemView.findViewById(R.id.textView_temp_name_value);
            mTextViewBeaconTempLink = itemView.findViewById(R.id.textView_temp_link_value);
            mTextViewBeaconLocation = itemView.findViewById(R.id.textView_location_value);
            mTextViewBeaconStatus = itemView.findViewById(R.id.textView_status_value);
        }
    }

    @NonNull
    @Override
    public AccountBeaconsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout beacon_view_row = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_walkinradius_account_beacon_list, parent, false);
        if (mBeaconSelectListener != null) {
            beacon_view_row.setBackground(parent.getResources().getDrawable(R.drawable.beacon_selector));
        }
        return new ViewHolder(beacon_view_row);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountBeaconsListAdapter.ViewHolder holder, final int position) {
        BeaconInfo beaconInfo = getBeacon(position);
        holder.mTextViewBeaconStatus.setText(beaconInfo.status);
        holder.mTextViewBeaconLocation.setText(beaconInfo.location);
        holder.mTextViewBeaconTempLink.setText(beaconInfo.temp_link);
        holder.mTextViewBeaconTempName.setText(beaconInfo.temp_name);
        holder.mTextViewBeaconModel.setText(beaconInfo.ibeacon_model_no);
        //holder.mTextViewBeaconUUID.setText(beaconInfo.uuid_no);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBeaconSelectListener != null) {
                    mBeaconSelectListener.onBeaconSelected(getBeacon(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeaconsInfo.size();
    }

    private BeaconInfo getBeacon(int position) {
        return mBeaconsInfo.get(position);
    }

    public interface BeaconSelectListener {
        void onBeaconSelected(BeaconInfo beaconInfo);
    }

}
