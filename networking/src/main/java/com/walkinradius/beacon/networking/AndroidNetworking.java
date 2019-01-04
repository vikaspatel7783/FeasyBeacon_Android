package com.walkinradius.beacon.networking;

public interface AndroidNetworking {

    interface Callback {

        void onSuccess(String message);

        void onFailure(String message);
    }

    void validateCredentials(String userName, String password, Callback callback);

    void getBeaconsInfo(Callback callback, String userName, String status);

}
