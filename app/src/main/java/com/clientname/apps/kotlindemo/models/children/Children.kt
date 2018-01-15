package com.clientname.apps.kotlindemo.models.children

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by razadar on 1/15/18.
 */
data class Children(
        @SerializedName("kind") @Expose val kind: String,
        @SerializedName("data") @Expose val data:Data
)