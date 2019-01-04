package com.walkinradius.beacon.networking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeaconInfo {

    @SerializedName("uuid_no")
    @Expose
    public String uuid_no;

    @SerializedName("ibeacon_model_no")
    @Expose
    public String ibeacon_model_no;

    @SerializedName("temp_name")
    @Expose
    public String temp_name;

    @SerializedName("temp_link")
    @Expose
    public String temp_link;

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("mac_id")
    @Expose
    public String MAC;
}
