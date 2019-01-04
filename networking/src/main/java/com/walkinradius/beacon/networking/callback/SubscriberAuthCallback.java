package com.walkinradius.beacon.networking.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.walkinradius.beacon.networking.AndroidNetworking;
import com.walkinradius.beacon.networking.model.Curdata;

import retrofit2.Call;
import retrofit2.Response;

public class SubscriberAuthCallback implements retrofit2.Callback<JsonObject> {

    private static final String SUCCESSFUL_LOGIN = "sucessfully login";
    private static final String LOGIN_FAILED = "username or password wrong";

    private final AndroidNetworking.Callback mCallback;

    public SubscriberAuthCallback(AndroidNetworking.Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

        Log.d(SubscriberAuthCallback.class.getSimpleName(), new Gson().toJson(response.body()));

        if (response.isSuccessful()) {

            Curdata authResponse = new Gson().fromJson(
                    response.body().getAsJsonArray(
                            "Curdata").get(0), Curdata.class);

            String authResponseMessage = authResponse.getMessage();

            if (authResponseMessage.equalsIgnoreCase(SUCCESSFUL_LOGIN)) {
                mCallback.onSuccess(authResponseMessage);
            } else if (authResponseMessage.equalsIgnoreCase(LOGIN_FAILED)) {
                mCallback.onFailure(authResponseMessage);
            } else {
                mCallback.onFailure("Different identifier: "+ authResponseMessage);
            }

        } else {
            mCallback.onFailure(response.message());
        }
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        mCallback.onFailure(t.getMessage());
    }
}
