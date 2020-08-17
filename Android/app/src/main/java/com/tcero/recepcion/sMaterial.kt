package com.tcero.recepcion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import com.tcero.recepcion.model.Material
import com.tcero.recepcion.util.appConfig
import kotlinx.android.synthetic.main.activity_s_material.*
import kotlinx.android.synthetic.main.material_item.*
import kotlinx.android.synthetic.main.material_item.view.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI

class sMaterial : AppCompatActivity() {

    var materiales:ArrayList<Material>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_material)
        var getMaterial = GetMaterial(this)
        this.materiales=ArrayList<Material>();
        getMaterial.execute("/api/materiales")
        lvMateriales.setOnItemClickListener { adapterView, view, i, l ->
            var nombre:String= view.tvNombre.text.toString()
            var data = Intent()
            var material = materiales!!.find { mat -> mat.nombre === nombre }
            data.putExtra("idmaterial",material!!.idmaterial)
            data.putExtra("nombre",material!!.nombre)
            data.putExtra("precio",material!!.precio)
            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }

    inner class GetMaterial: AsyncTask<String,String,List<Material>> {

        var context: Context?=null
        val uri = appConfig.uri()

        constructor(context:Context) {
            this.context = context
        }

        override fun doInBackground(vararg path: String?): ArrayList<Material>? {
            var url = uri?.resolve(URI(path[0]))?.toURL()
            var urlConnect = url?.openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 5000
            urlConnect.requestMethod = "GET"
            var sharedPref = context!!.getSharedPreferences("recepcion", Context.MODE_PRIVATE)
            var bearer = sharedPref.getString("Authorization", null)

            bearer!!.let { it ->  urlConnect.setRequestProperty("Authorization", "Bearer " + it )}
            var inString = ConvertStreamToString(urlConnect.inputStream)
            publishProgress(inString)
            return materiales
        }

        private fun ConvertStreamToString(inputStream: InputStream): String {
            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line:String?
            var allString:String=""
            try {
                do {
                    line = bufferReader.readLine()
                    if (line != null){
                        allString += line
                    }
                } while(line!=null)
            } catch (e:Exception) {
                e.printStackTrace()
            }
            return allString
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var arr = JSONArray(values[0])
                for(i in 0..arr.length()-1) {
                    val mat = arr.getJSONObject(i)
                    val material = Material(mat.getInt("idmaterial"),
                        mat.getString("nombre"),
                        mat.getDouble("precio"))
                    materiales?.add(material)
                }
            } catch (e:java.lang.Exception) {
                e.printStackTrace();
            }
        }

        override fun onPostExecute(result: List<Material>?) {
            var listMaterialAdapter = ListMaterialAdapter(context,materiales)
            lvMateriales.adapter = listMaterialAdapter
            svMaterial.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    listMaterialAdapter.filter.filter(p0)
                    return false
                }

            })
        }

        inner class ListMaterialAdapter:BaseAdapter, Filterable {
            var materiales:ArrayList<Material>?=null

            var context:Context?=null

            constructor(context:Context?, materiales:ArrayList<Material>?) {
                this.context = context
                this.materiales = materiales
            }

            override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var materialesView = inflator.inflate(R.layout.material_item,null)
                materialesView.tvNombre.setText(materiales!!.get(p0).nombre)

                return materialesView
            }

            override fun getItem(p0: Int): Any {
                return materiales?.get(p0) as Any
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun getCount(): Int {
                return materiales!!.size
            }

            override fun getFilter(): Filter {
                return object : Filter() {
                    override fun performFiltering(p0: CharSequence?): FilterResults {
                        TODO("Not yet implemented")
                    }

                    override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                        TODO("Not yet implemented")
                    }

                }
            }
        }
    }
}