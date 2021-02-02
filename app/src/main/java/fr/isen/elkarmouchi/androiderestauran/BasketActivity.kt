package fr.isen.elkarmouchi.androiderestauran
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.elkarmouchi.androiderestauran.basket.*
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityBasketBinding
import fr.isen.elkarmouchi.androiderestauran.detail.DetailViewFragment
import fr.isen.elkarmouchi.androiderestauran.network.NetworkConstant
import fr.isen.elkarmouchi.androiderestauran.register.RegisterActivity
import org.json.JSONObject

class BasketActivity : AppCompatActivity(),
    BasketCellInterface {
    lateinit var binding: ActivityBasketBinding
    lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket = Basket.getBasket(this)
        //reloadData(basket)
        val fragment = BasketItemsFragment(basket, this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()

        binding.orderButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
        }
    }


    override fun onDeleteItem(item: BasketItem) {
        //val basket = Basket.getBasket(this)
        //val itemToDelete=basket.items.firstOrNull { it.dish.name== item.dish.name }
        basket.items.remove(item)
        basket.save(this)
       // reloadData(basket)
    }

    override fun onShowDetail(item: BasketItem) {
        val fragment = DetailViewFragment(item.dish)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if(idUser != -1) {
                sendOrder(idUser)
            }
        }
    }
    private fun sendOrder(idUser: Int) {
        val message = basket.items.map { "${it.count}x ${it.dish.name}" }.joinToString("\n")
        //basket.clear()
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.ID_USER, idUser)
        jsonData.put(NetworkConstant.MSG, message)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Votre commande bien enregistré")
                builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                    basket.clear()
                    basket.save(this)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                builder.show()
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                    Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)
    }
}



