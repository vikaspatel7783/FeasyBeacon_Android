package com.walkinradius.beacon.networking.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    static final String BASE_URL_WALKIN_RADIUS = "http://walkinradius.com/myaccount/mobile_services/";

    private static Retrofit mRetrofit;

    static  {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_WALKIN_RADIUS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceApi getServiceApi() {
        return mRetrofit.create(ServiceApi.class);
    }

}
