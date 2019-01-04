package com.walkinradius.beacon.networking.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Curdata {

    @SerializedName("c_id")
    @Expose
    private String cId;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }
}
