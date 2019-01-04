package com.walkinradius.beacon.networking.retrofit;

import com.google.gson.JsonObject;
import com.walkinradius.beacon.networking.model.BeaconInfo;
import com.walkinradius.beacon.networking.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("client_login.php")
        //@FormUrlEncoded
    Call<JsonObject> validateSubscriber(@Query("username") String userName,
                                        @Query("password") String password);

    //@GET("get_template_link.php?username=98119&&status=Active")
    @GET("get_template_link.php")
    Call<List<BeaconInfo>> getBeaconInfo(@Query("username") String userName,
                                         @Query("status") String status);

    @POST("/posts")
        //@FormUrlEncoded
    Call<Note> saveNote(/*@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId*/
            @Body Note note);

}
