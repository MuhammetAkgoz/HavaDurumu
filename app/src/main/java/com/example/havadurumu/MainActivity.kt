package com.example.havadurumu

import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var locasion:SimpleLocation?=null
    var lalitute:String?=null
    var longute:String?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myAdapter=ArrayAdapter.createFromResource(this,R.array.sehirler,android.R.layout.simple_spinner_item)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnSehirler.adapter=myAdapter
        spnSehirler.setOnItemSelectedListener(this)


        locasion=SimpleLocation(this)
        spnSehirler.setSelection(6)
        verileriGetir("Konya")

    }


    fun verileriGetir(sehir:String){

        val url="https://api.openweathermap.org/data/2.5/weather?q="+sehir+"&appid=0459a1ecaa5f70a7688b0e8da1c40499&lang=tr"
        val havaDurumu=JsonObjectRequest(Request.Method.GET,url,null, object : Response.Listener<JSONObject> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(response: JSONObject?) {
                var main= response?.getJSONObject("main")
                var sicaklik= main?.getString("temp")
                tvSıcaklık.text=sicaklik
                var sehirAdi= response?.getString("name")
                tvSehir.text=sehirAdi

                var weather= response?.getJSONArray("weather")
                var aciklama= weather?.getJSONObject(0)?.getString("description")
                tvAcıklama.text=aciklama

                var icon= weather?.getJSONObject(0)?.getString("icon")

                if (icon?.last() == 'd'){
                    rootLayout.background=getDrawable(R.drawable.bg)
                    tvAcıklama.setTextColor(resources.getColor(R.color.Black))
                    tvC.setTextColor(resources.getColor(R.color.Black))
                    tvSehir.setTextColor(resources.getColor(R.color.Black))
                    tvTarih.setTextColor(resources.getColor(R.color.Black))
                    tvSıcaklık.setTextColor(resources.getColor(R.color.Black))

                }else{
                    rootLayout.background=getDrawable(R.drawable.gece)
                    tvAcıklama.setTextColor(resources.getColor(R.color.white))
                    tvC.setTextColor(resources.getColor(R.color.white))
                    tvSehir.setTextColor(resources.getColor(R.color.white))
                    tvTarih.setTextColor(resources.getColor(R.color.white))
                    tvSıcaklık.setTextColor(resources.getColor(R.color.white))
                    spnSehirler.setBackgroundColor(resources.getColor(R.color.white))
                }

                var resimDosyaAdi=resources.getIdentifier("icon_"+icon.sonKarakterSil(),"drawable",packageName)
                imghavaDurumu.setImageResource(resimDosyaAdi)

                tvTarih.text=tarihYaz()

            }

        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {



            }

        })

        MySinglitıon.MySingleton.getInstance(this).addToRequestQueue(havaDurumu)

    }
    fun locasionlaGetir(lat:String?,long:String?){

        val url="https://api.openweathermap.org/data/2.5/weather?lat"+lat+"&lon="+long+"&appid=0459a1ecaa5f70a7688b0e8da1c40499&lang=tr"
        val havaDurumu2=JsonObjectRequest(Request.Method.GET,url,null, object : Response.Listener<JSONObject> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(response: JSONObject?) {
                var main= response?.getJSONObject("main")
                var sicaklik= main?.getString("temp")
                tvSıcaklık.text=sicaklik
                var sehirAdi= response?.getString("name")
                tvSehir.text=sehirAdi

                var weather= response?.getJSONArray("weather")
                var aciklama= weather?.getJSONObject(0)?.getString("description")
                tvAcıklama.text=aciklama

                var icon= weather?.getJSONObject(0)?.getString("icon")

                if (icon?.last() == 'd'){
                    rootLayout.background=getDrawable(R.drawable.bg)
                    tvAcıklama.setTextColor(resources.getColor(R.color.Black))
                    tvC.setTextColor(resources.getColor(R.color.Black))
                    tvSehir.setTextColor(resources.getColor(R.color.Black))
                    tvTarih.setTextColor(resources.getColor(R.color.Black))
                    tvSıcaklık.setTextColor(resources.getColor(R.color.Black))

                }else{
                    rootLayout.background=getDrawable(R.drawable.gece)
                    tvAcıklama.setTextColor(resources.getColor(R.color.white))
                    tvC.setTextColor(resources.getColor(R.color.white))
                    tvSehir.setTextColor(resources.getColor(R.color.white))
                    tvTarih.setTextColor(resources.getColor(R.color.white))
                    tvSıcaklık.setTextColor(resources.getColor(R.color.white))
                    spnSehirler.setBackgroundColor(resources.getColor(R.color.white))
                }

                var resimDosyaAdi=resources.getIdentifier("icon_"+icon.sonKarakterSil(),"drawable",packageName)
                imghavaDurumu.setImageResource(resimDosyaAdi)

                tvTarih.text=tarihYaz()

            }

        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {



            }

        })

        MySinglitıon.MySingleton.getInstance(this).addToRequestQueue(havaDurumu2)


    }

    fun tarihYaz():String{

        var takvim= Calendar.getInstance().time
        var formatlayici=SimpleDateFormat("EEE,MMM,YYY", Locale("tr"))
        var tarih=formatlayici.format(takvim)

        return tarih
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    // adapterr bos oludunda calısır.
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    // secilen şehrin atama işlemini yaptık.
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var secilenSehir=parent?.getItemAtPosition(position).toString()
        verileriGetir(secilenSehir)

        // buraya izin kısmı gelicek
        }

    }




private fun String?.sonKarakterSil(): Any? {

        //50n ifadesini 50 olarak geri gonderir.
        return this?.substring(0, this!!.length -1)
}
