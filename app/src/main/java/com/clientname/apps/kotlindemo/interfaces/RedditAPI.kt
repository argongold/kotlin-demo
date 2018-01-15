package com.clientname.apps.kotlindemo.interfaces

import com.clientname.apps.kotlindemo.models.Feed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by razadar on 1/15/18.
 */
interface RedditAPI {
    @Headers("Content-type: application/json")
    @GET(".json")
    fun getData(): Call<Feed>;
}