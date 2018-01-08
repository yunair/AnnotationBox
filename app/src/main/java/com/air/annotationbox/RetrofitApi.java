package com.air.annotationbox;

import com.air.annotationbox.custom.annotations.AGET;
import com.air.annotationbox.custom.annotations.AHTTP;
import com.air.annotationbox.custom.annotations.AHeaders;
import com.air.annotationbox.custom.annotations.APath;

import retrofit2.Response;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by air on 8/13/17.
 */

public interface RetrofitApi {
    @AGET("aaa")
    @Headers("Cache-Controll:max-age=12312321")
    Response<Void> getTest(@APath(value = "adasdas", encoded = true) String test);

    @POST("bbb")
    @AHeaders({"aaa", "bbb", "ccc"})
    @AHTTP(method = "GET", path = "1", hasBody = true)
    Response<Void> postTest(@Path("asdsad") String value);
}

