package com.cassby.cassbysdk.Activity;

import com.cassby.cassbysdk.NetworkModel.ActivityResponse;
import com.cassby.cassbysdk.NetworkModel.ActivityRoot;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostActivityApi {

    @POST("/activity")
    Observable<ActivityResponse> postActivity(@Query("token") String token, @Body ActivityRoot request);
}
