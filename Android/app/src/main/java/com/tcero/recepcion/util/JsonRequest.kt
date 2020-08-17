package com.tcero.recepcion.util

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class JsonRequest : JsonObjectRequest {

    var context:Context?=null

    constructor(context:Context, method: Int, url: String?, jsonRequest: JSONObject?,
                listener: Response.Listener<JSONObject>?, errorListener: Response.ErrorListener? ) :
            super(method, appConfig.uri().toString() +  url, jsonRequest, listener, errorListener) {
        this.context = context
    }

    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        var sharedPref:SharedPreferences?=context!!.getSharedPreferences("recepcion",Context.MODE_PRIVATE)
        var editor = sharedPref!!.edit()
        val auth = sharedPref!!.getString("Authorization",null)
        auth?.let { headers["Authorization"] = it }
//        headers["Authorization"] = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTk3MDczNjkwLCJleHAiOjE1OTc2Nzg0OTB9.XlNwndyJas4Iho_7NTIyllE6naywSmyHNyjsqbdkohhzNVDDg_u_kPA3SAJTGHvHv611oTgKBnvM9bRf3FQDdQ"
        return headers
    }

}


