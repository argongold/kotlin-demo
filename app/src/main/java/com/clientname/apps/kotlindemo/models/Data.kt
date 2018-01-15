package com.clientname.apps.kotlindemo.models

import com.clientname.apps.kotlindemo.models.children.Children
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by razadar on 1/15/18.
 */
data class Data(
        @SerializedName("modhash") @Expose val modhash:String,
        @SerializedName("children") @Expose val children:ArrayList<Children>
)