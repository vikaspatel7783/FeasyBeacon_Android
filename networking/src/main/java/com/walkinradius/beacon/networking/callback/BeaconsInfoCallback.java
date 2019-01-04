package com.walkinradius.beacon.networking.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.walkinradius.beacon.networking.AndroidNetworking;
import com.walkinradius.beacon.networking.model.BeaconInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BeaconsInfoCallback implements retrofit2.Callback<List<BeaconInfo>> {

    private final AndroidNetworking.Callback mCallback;

    public BeaconsInfoCallback(AndroidNetworking.Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call<List<BeaconInfo>> call, Response<List<BeaconInfo>> response) {
        String jsonResponse = new Gson().toJson(response.body());
        Log.d(BeaconsInfoCallback.class.getSimpleName(), jsonResponse);

        if (response.isSuccessful()) {
            mCallback.onSuccess(jsonResponse); // json string of List<BeaconInfo>
        } else {
            mCallback.onFailure(response.message());
        }
    }

    private String getDisplayString(BeaconInfo beaconInfo) {
        return beaconInfo.ibeacon_model_no + "\n" +
                beaconInfo.location +"\n"+
                beaconInfo.status +"\n"+
                beaconInfo.temp_link +"\n"+
                beaconInfo.uuid_no;
    }

    @Override
    public void onFailure(Call<List<BeaconInfo>> call, Throwable t) {
        mCallback.onFailure(t.getMessage());
    }
}
