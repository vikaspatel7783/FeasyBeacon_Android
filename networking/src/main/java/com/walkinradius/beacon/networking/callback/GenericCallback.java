package com.walkinradius.beacon.networking.callback;

public interface GenericCallback<T> {

    void onSuccess(T message);

    void onFailure(T message);

}
