package com.clientname.apps.kotlindemo.services

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import com.clientname.apps.kotlindemo.R
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by razadar on 1/14/18.
 */
class ImageDownloadService(val name:String = "ImageDownloadService"): IntentService(name) {

    companion object {
        val LOGGER = LoggerFactory.getLogger(ImageDownloadService::class.java.name)
    }

    override fun onHandleIntent(intent: Intent?) {

        val intentAction: String? = intent?.action
        LOGGER.info("Intent Received: $intentAction")

        if(intentAction.equals(resources.getString(R.string.image_download_action))){

            val url_link:String? = intent?.getStringExtra(resources.getString(R.string.image_url))
            LOGGER.info("URL is : $url_link")

            /* call download function to download the image now*/
            downloadImage(url_link)


            /* send image download complete intent now*/
            var downloadCompleteIntent: Intent = Intent()
            downloadCompleteIntent.action = resources.getString(R.string.image_download_complete)
            LOGGER.info("sending broadcast image download complete")
            sendBroadcast(downloadCompleteIntent)

        }

    }

    fun downloadImage(urlLink :String? ){

        val url: URL = URL(urlLink)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        LOGGER.info("ConnectionResponse = ${connection.responseCode}")

        /* open output file to save the image stream*/
        val path: File = applicationContext.filesDir
        val file = File(path,"imagefile.jpg")
        LOGGER.info("filePath: ${file.absolutePath}")

        if(!file.exists()){
            /* file doesn't exists,
             so create it first*/
            file.createNewFile()
        }
        val outfile: FileOutputStream = FileOutputStream(file)

        val inputstream: InputStream = connection.inputStream

        var buffer: ByteArray = ByteArray(1024)

        var numberofbytes: Int = inputstream.read(buffer)

        while(numberofbytes> 0){

            outfile.write(buffer,0,numberofbytes)
            numberofbytes = inputstream.read(buffer)

        }

        /* flush and close outputfile now*/
        outfile.flush()
        outfile.close()

        /*close inputstream as well*/
        inputstream.close()

        LOGGER.info("Download Image Success")


    }
}