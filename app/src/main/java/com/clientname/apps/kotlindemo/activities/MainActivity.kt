package com.clientname.apps.kotlindemo.activities


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.clientname.apps.kotlindemo.R
import com.clientname.apps.kotlindemo.fragments.ServiceDemo
import com.clientname.apps.kotlindemo.interfaces.RedditAPI
import com.clientname.apps.kotlindemo.models.Feed
import com.clientname.apps.kotlindemo.services.ImageDownloadService
import kotlinx.android.synthetic.main.activity_main.*
import org.slf4j.LoggerFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created by razadar on 1/12/18.
 */
class MainActivity: AppCompatActivity() {

    companion object {
        val LOGGER = LoggerFactory.getLogger(MainActivity::class.java.name)
    }

    val mContext: Context = this

   lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOGGER.info(resources.getString(R.string.entering))

        setContentView(R.layout.activity_main)

        /* set title text*/
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){

            tv_title.setText(Html.fromHtml(resources.getString(R.string.welcome),Html.FROM_HTML_MODE_COMPACT))
        } else {
            tv_title.setText(Html.fromHtml(resources.getString(R.string.welcome)))

        }

        receiver = getBroadCastReceiver()


        LOGGER.info(resources.getString(R.string.leaving))

    }



    override fun onStart() {
        super.onStart()
        LOGGER.info(resources.getString(R.string.entering))
        LOGGER.info(resources.getString(R.string.leaving))
    }

    override fun onPause(){
        super.onPause()
        LOGGER.info(resources.getString(R.string.entering))
        LOGGER.info(resources.getString(R.string.leaving))
    }

    override fun onResume() {
    super.onResume()
        LOGGER.info(resources.getString(R.string.entering))
       addReceiver(receiver)
        LOGGER.info(resources.getString(R.string.leaving))
    }

    override fun onStop() {
        LOGGER.info(resources.getString(R.string.entering))
       removeReceiver(receiver)
        LOGGER.info(resources.getString(R.string.leaving))
        super.onStop()
    }

    override fun onDestroy() {
        LOGGER.info(resources.getString(R.string.entering))
        LOGGER.info(resources.getString(R.string.leaving))
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        LOGGER.info(resources.getString(R.string.entering))
        LOGGER.info(resources.getString(R.string.leaving))
    }


    fun buttonPressAction(view: View){

        when(view.id){
            R.id.bt_service_demo ->{
                var fm: FragmentManager = supportFragmentManager
                var ft: FragmentTransaction = fm.beginTransaction()
                ft.replace(R.id.view_root,ServiceDemo(),"ServiceDemo")
                ft.addToBackStack(null)
                ft.commit()
            }
            R.id.bt_retrofit_demo -> {
                Toast.makeText(mContext,resources.getString(R.string.fetching_json_feed),Toast.LENGTH_SHORT).show()
                getFeedData()

            }
            R.id.bt_realm_demo -> Toast.makeText(mContext,resources.getString(R.string.function_not_implemented),Toast.LENGTH_SHORT).show()
            R.id.bt_send_intent -> {

                mContext.startService(buildDownloadIntent())
                var im: View? = supportFragmentManager.findFragmentByTag("ServiceDemo").view
                var imageview: ImageView? = im?.findViewById(R.id.image_view)
                imageview?.visibility = View.GONE
                var pbar: ProgressBar? = im?.findViewById(R.id.progressbar)
                pbar?.visibility = View.VISIBLE
            }

        }
    }

    fun getBroadCastReceiver():BroadcastReceiver{

        return object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {


                LOGGER.info("IntentReceived: $intent?.action")
                when(intent?.action){

                    resources.getString(R.string.image_download_complete) ->{

                        /* update image view with the downloaded png here*/
                        var im: View? = supportFragmentManager.findFragmentByTag("ServiceDemo").view
                        var pbar: ProgressBar? = im?.findViewById(R.id.progressbar)
                        pbar?.visibility = View.GONE

                        val path:String? = mContext.filesDir.absolutePath + File.separator + "imagefile.jpg"
                        LOGGER.info("filePath: $path")

                        var bitmap: Bitmap = BitmapFactory.decodeFile(path)
                        var imageview: ImageView? = im?.findViewById(R.id.image_view)
                        imageview?.setImageBitmap(bitmap)
                        imageview?.visibility = View.VISIBLE
                    }

                }

            }

        }

    }

    fun addReceiver(receiver: BroadcastReceiver){

        var intentFilter: IntentFilter = IntentFilter(resources.getString(R.string.image_download_complete))
        registerReceiver(receiver,intentFilter)
    }

    fun removeReceiver(receiver: BroadcastReceiver){
        unregisterReceiver(receiver)
    }


    fun buildDownloadIntent():Intent{
        val intent:Intent = Intent(mContext,ImageDownloadService::class.java)
        intent.action = resources.getString(R.string.image_download_action)
        intent.putExtra(resources.getString(R.string.image_url),resources.getString(R.string.image_url_link))
        return intent
    }

    /* A function to initialize retrofit instance and fetch feed data*/
    fun getFeedData(){

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val redditAPI: RedditAPI = retrofit.create(RedditAPI::class.java)
        var call: Call<Feed> =  redditAPI.getData()

        call.enqueue(object: Callback<Feed>{

            override fun onResponse(call: Call<Feed>?, response: Response<Feed>?) {

                LOGGER.info("onResponse: ${response?.body()}")
                Toast.makeText(mContext,"${response?.body()}",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Feed>?, t: Throwable?) {

                LOGGER.error("onFailure: ${t?.message}")
                Toast.makeText(mContext,resources.getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show()
            }

        })
    }

}