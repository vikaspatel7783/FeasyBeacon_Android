package com.walkinradius.beacon.networking.retrofit;

import com.google.gson.JsonObject;
import com.walkinradius.beacon.networking.AndroidNetworking;
import com.walkinradius.beacon.networking.callback.BeaconsInfoCallback;
import com.walkinradius.beacon.networking.callback.SubscriberAuthCallback;
import com.walkinradius.beacon.networking.model.BeaconInfo;
import com.walkinradius.beacon.networking.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RetrofitNetworking implements AndroidNetworking {

    private ServiceApi serviceApi = RetrofitFactory.getServiceApi();

    private Callback mCallback;

    @Override
    public void validateCredentials(String userName, String password, Callback callback) {

        this.mCallback = callback;

        Call<JsonObject> subscriberCredentialsCall = serviceApi.validateSubscriber(userName, password);

        subscriberCredentialsCall.enqueue(new SubscriberAuthCallback(mCallback));
        //serviceApi.saveNote(getNote()).enqueue(noteCallback);

    }

    @Override
    public void getBeaconsInfo(Callback callback, String userName, String status) {
        this.mCallback = callback;

        Call<List<BeaconInfo>> beaconInfo = serviceApi.getBeaconInfo(userName, status);
        beaconInfo.enqueue(new BeaconsInfoCallback(mCallback));
    }

    retrofit2.Callback<Note> noteCallback = new retrofit2.Callback<Note>() {

        @Override
        public void onResponse(Call<Note> call, Response<Note> response) {
            if (response.isSuccessful()) {
                mCallback.onSuccess(response.body().getBody());
            }
        }

        @Override
        public void onFailure(Call<Note> call, Throwable t) {
                mCallback.onFailure(t.getMessage());
        }
    };


    private Note getNote() {
        Note note = new Note();

        note.setTitle("Title-Vikas");
        note.setBody("Body1");
        note.setUserId(1);

        return note;
    }

}
