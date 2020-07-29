package com.colinmobile.umrah

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.text.SimpleDateFormat
import java.time.temporal.TemporalAdjusters.next
import java.util.*


class SalatActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salat)
        title = "Salat Schedule"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val tv_subuh = findViewById<TextView>(R.id.value_subuh)
        val tv_dzuhur = findViewById<TextView>(R.id.value_dzuhur)
        val tv_asar = findViewById<TextView>(R.id.value_asar)
        val tv_magrib = findViewById<TextView>(R.id.value_magrib)
        val tv_isya = findViewById<TextView>(R.id.value_isya)

        tv_subuh.setText(intent.getStringExtra("subuh"))
        tv_dzuhur.setText(intent.getStringExtra("dzuhur"))
        tv_asar.setText(intent.getStringExtra("asar"))
        tv_magrib.setText(intent.getStringExtra("magrib"))
        tv_isya.setText(intent.getStringExtra("isya"))

        val ivPlay = findViewById<AppCompatImageView>(R.id.iv_play)
        val mPlayer: MediaPlayer = MediaPlayer.create(this@SalatActivity, R.raw.adzan)
        ivPlay.setOnClickListener({
            if(mPlayer.isPlaying){
                ivPlay.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_play))
                mPlayer.stop()
                mPlayer.prepare()
            }else{
                ivPlay.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_pause))
                mPlayer.start()
            }
        })

        val imgShare = findViewById<AppCompatImageView>(R.id.img_share)
        imgShare.setOnClickListener(View.OnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Jadwal salat mekah hari ini \n "+
                        resources.getString(R.string.subuh)+ " : "+tv_subuh.text+" \n " +
                        resources.getString(R.string.dzuhur)+ " : "+tv_dzuhur.text+" \n "+
                        resources.getString(R.string.asar)+ " : "+tv_asar.text+" \n "+
                        resources.getString(R.string.magrib)+ " : "+tv_magrib.text+" \n "+
                        resources.getString(R.string.isya)+ " : "+tv_isya.text+""
                )
                type = "text/plain"
            }
            startActivity(sendIntent)
        })


        Log.i("subuhTime", "Time : "+tv_subuh.text)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}