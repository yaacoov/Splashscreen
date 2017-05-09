package com.appdvl.yaacoov.splashscreen.model.callback;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yaacoov on 08/05/17.
 * Splashscreen.
 */

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactResponse
    */
    @GET("contacts/")
    Call<ContactResponse> getMyJSON();


}
