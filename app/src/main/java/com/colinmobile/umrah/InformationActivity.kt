package com.colinmobile.umrah

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import kotlinx.android.synthetic.main.activity_information.*
import java.util.*


class InformationActivity : BaseActivity() {
    lateinit var rgLanguage:RadioGroup
    lateinit var rbEnglish:RadioButton
    lateinit var rbIndonesia:RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        title = resources.getString(R.string.information)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rgLanguage = findViewById(R.id.rg_language) as RadioGroup
        rbEnglish = findViewById(R.id.rb_english)as RadioButton
        rbIndonesia = findViewById(R.id.rb_indonesia)as RadioButton

        if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ConfigurationCompat.getLocales(resources.configuration)[0]==Locale("en")
            } else {
                resources.configuration.locale.equals("en")
            }
        ){
            rgLanguage.clearCheck()
            rbEnglish.setChecked(true)
        }else
            if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ConfigurationCompat.getLocales(resources.configuration)[0]==Locale("id")
                } else {
                    resources.configuration.locale.equals("id")
                }
            ){
                rgLanguage.clearCheck()
                rbIndonesia.setChecked(true)
            }

        rgLanguage.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_english -> {
                        val myLocale = Locale("en")
                        val res: Resources = resources
                        val dm: DisplayMetrics = res.getDisplayMetrics()
                        val conf: Configuration = res.getConfiguration()
                        conf.locale = myLocale
                        res.updateConfiguration(conf, dm)
                        rgLanguage.check(R.id.rb_english)
//                        changeLocale(parent.applicationContext,"en")

                        val refresh = Intent(this@InformationActivity, MainActivity::class.java)
                        finish()
                        startActivity(refresh)
                    }
                    R.id.rb_indonesia -> {
                        val myLocale = Locale("id")
                        val res: Resources = resources
                        val dm: DisplayMetrics = res.getDisplayMetrics()
                        val conf: Configuration = res.getConfiguration()
                        conf.locale = myLocale
                        res.updateConfiguration(conf, dm)
                        rgLanguage.check(R.id.rb_indonesia)
//                        changeLocale(parent.applicationContext,"id")

                        val refresh = Intent(this@InformationActivity, MainActivity::class.java)
                        finish()
                        startActivity(refresh)
                    }
                    else -> {
                        val myLocale = Locale("en")
                        val res: Resources = resources
                        val dm: DisplayMetrics = res.getDisplayMetrics()
                        val conf: Configuration = res.getConfiguration()
                        conf.locale = myLocale
                        res.updateConfiguration(conf, dm)

                        rgLanguage.check(R.id.rb_english)
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}