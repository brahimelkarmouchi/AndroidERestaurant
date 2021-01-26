package fr.isen.elkarmouchi.androiderestauran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.elkarmouchi.androiderestauran.Category.CategoryAdapter
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityCategoryBinding
import fr.isen.elkarmouchi.androiderestauran.network.MenuResult
import fr.isen.elkarmouchi.androiderestauran.network.NetworkConstant
import kotlinx.android.synthetic.main.activity_category.*
import org.json.JSONObject


enum class ItemType {
    STARTER, MAIN, DESSERT
}
class CategoryActivity : AppCompatActivity() {
    private lateinit var bindind: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCategoryBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_category)
        setContentView(bindind.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        bindind.categoryTitle.text = getCategoryTitle(selectedItem)
        loadList()
        makeRequest()
        Log.d("lifecycle", "onCreate")


    }

    private fun loadList() {
        var entries = listOf<String>("salade","boeuf","glace")
        val adapter =
            CategoryAdapter(
                entries
            )
        bindind.recyclerView.layoutManager = LinearLayoutManager(this)
        bindind.recyclerView.adapter = adapter
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val jsondata= JSONObject()
        jsondata.put("id_shop", 1)
       // val url = "http://test.api.catering.bluecodegames.com/menu"
        var url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

// Request a string response from the provided URL.
        val stringRequest= JsonObjectRequest(Request.Method.POST,
            url,
            jsondata,
            { response ->
                //Log.d("request",response.toString(2))
                val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                menu.data.forEach{
                    Log.d("Request", it.name)
                }
            },
            {error ->
                Log.d("Request", error.localizedMessage)
            }
            )



// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    
    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.STARTER -> getString(
                R.string.starter
            )
            ItemType.MAIN -> getString(
                R.string.main
            )
            ItemType.DESSERT -> getString(
                R.string.deserts
            )
            else -> ""
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        super.onDestroy()
    }
}