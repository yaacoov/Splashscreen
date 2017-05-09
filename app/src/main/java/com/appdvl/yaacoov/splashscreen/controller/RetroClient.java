package com.appdvl.yaacoov.splashscreen.controller;

import com.appdvl.yaacoov.splashscreen.model.callback.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yaacoov on 08/05/17.
 * Splashscreen.
 */

public class RetroClient {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://api.androidhive.info/";

    /**
     * Get Retrofit Instance
     */
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}