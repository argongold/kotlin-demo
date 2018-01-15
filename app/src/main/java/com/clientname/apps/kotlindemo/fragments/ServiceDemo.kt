package com.clientname.apps.kotlindemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clientname.apps.kotlindemo.R
import org.slf4j.LoggerFactory

/**
 * Created by razadar on 1/13/18.
 */
class ServiceDemo: Fragment() {




    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater?.inflate(R.layout.frament_service_demo,container,false)

    }

}