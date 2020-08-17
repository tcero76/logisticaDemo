package com.tcero.recepcion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.Response
import com.tcero.recepcion.util.JsonRequest
import com.tcero.recepcion.util.RequestsQueue
import com.tcero.recepcion.viewmodel.RecepcionViewModel
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val username = findViewById<EditText>(R.id.tvUsuario)
    val password = findViewById<EditText>(R.id.tvPassword)
    val login = findViewById<Button>(R.id.bLogin)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val recepcionViewModel = RecepcionViewModel()

        username.addTextChangedListener(object:TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                recepcionViewModel.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

        })

    }

    fun login(view: View) {
        var log = JSONObject()
            .put("usuario", username.text.toString())
            .put("clave", password.text.toString())
        var jsonRequest = JsonRequest(this, Request.Method.POST, "/api/signin",
            log,
            Response.Listener { res ->
                logged(res)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })
        RequestsQueue.getInstance(this).addToRequestQueue(jsonRequest)
    }

    private fun logged(res: JSONObject?) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("usuario", res!!.getString("nombre"))
        intent.putExtra("almacen", res!!.getString("almacen"))
        var sharedPref=this.getSharedPreferences("recepcion", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString("Authorization", res!!.getString("accessToken"))
        editor.commit()
        startActivity(intent)
    }

}