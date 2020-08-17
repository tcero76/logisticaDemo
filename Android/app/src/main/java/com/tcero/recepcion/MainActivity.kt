package com.tcero.recepcion

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.tcero.recepcion.util.JsonRequest
import com.tcero.recepcion.util.RequestsQueue
import com.tcero.recepcion.util.appConfig
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getMaterial(view: View) {
        var intent = Intent(this, sMaterial::class.java)
        startActivityForResult(intent, 201)
    }

    fun submitOr(view:View) {
        val url = "/ors"
        var oritem = JSONObject()
            .put("idmaterial",1)
            .put("cantidad", 1.6)
            .put("pos",1)
        var oritems = JSONArray().put(oritem)
        var orec = JSONObject()
            .put("guiadedespacho",1)
            .put("oritems", oritems)

        val jsonObjectRequest = JsonRequest(
            this,
            Request.Method.POST, url, orec,
            Response.Listener { response ->
                println("Response: %s".format(response.toString()))
            },
            Response.ErrorListener { error ->
                println(error.toString())
            }
        )
    RequestsQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode===201) {
            if (resultCode === Activity.RESULT_OK) {
                bMaterial.setText(data!!.getStringExtra("nombre"))
                var idmaterial = data!!.getIntExtra("idmaterial",0)
            }
        }

    }
}