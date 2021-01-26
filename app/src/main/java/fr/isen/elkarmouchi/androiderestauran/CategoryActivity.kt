package fr.isen.elkarmouchi.androiderestauran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.elkarmouchi.androiderestauran.Category.CategoryAdapter
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityCategoryBinding
import fr.isen.elkarmouchi.androiderestauran.network.Dish
import fr.isen.elkarmouchi.androiderestauran.network.MenuResult
import fr.isen.elkarmouchi.androiderestauran.network.NetworkConstant
import org.json.JSONObject


enum class ItemType {
    STARTER, DESSERT, MAIN;

    companion object {
        fun  categoryTitle(item: ItemType?): String{
            return when(item){
                STARTER -> "Entrées"
                MAIN -> "Plats"
                DESSERT -> "Desserts"
                else -> ""
            }
        }
    }
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
        //loadList()
        makeRequest(selectedItem)
        Log.d("lifecycle", "onCreate")


    }

    private fun makeRequest(selectedItem: ItemType?){
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
                val items = menu.data.firstOrNull {it.name == ItemType.categoryTitle(selectedItem)}
                loadList(items?.items)


            },
            {error ->
                Log.d("Request", error.localizedMessage)
            }
            )

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    private fun loadList(dishes:List<Dish>?) {
        //val entries = listOf<String>("salade","boeuf","glace")
        val entries = dishes?.map {it.name}
        entries?.let{
            val adapter =
                CategoryAdapter(
                    entries
                )
            bindind.recyclerView.layoutManager = LinearLayoutManager(this)
            bindind.recyclerView.adapter = adapter
        }

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
                R.string.dessert
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