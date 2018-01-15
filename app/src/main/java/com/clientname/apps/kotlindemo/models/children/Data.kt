package com.clientname.apps.kotlindemo.models.children

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by razadar on 1/15/18.
 */
data class Data(
        @SerializedName("contest_mod") @Expose val contest_mod: String ,
        @SerializedName("subreddit") @Expose val subreddit: String,
        @SerializedName("author") @Expose val author: String
)