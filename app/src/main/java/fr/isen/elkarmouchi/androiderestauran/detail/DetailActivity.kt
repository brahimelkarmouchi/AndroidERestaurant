package fr.isen.elkarmouchi.androiderestauran.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.elkarmouchi.androiderestauran.BaseActivity
import fr.isen.elkarmouchi.androiderestauran.R
import fr.isen.elkarmouchi.androiderestauran.basket.Basket
import fr.isen.elkarmouchi.androiderestauran.basket.BasketItem
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityDetailBinding
import fr.isen.elkarmouchi.androiderestauran.network.Dish
import kotlin.math.max

class DetailActivity : BaseActivity() {

    companion object{
        const val EXTRA_DISH = "EXTRA_DISH"
        const val NBR_ITEMS="NBR_ITEMS"
        const val USER_PREFERENCES_NAME="USER_PREFERENCES_NAME"
    }

    lateinit var binding: ActivityDetailBinding
    private var itemCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val dish = intent.getSerializableExtra(EXTRA_DISH) as? Dish
        dish?.let {
            setupView(it)
        }


    }
    private fun setupView(dish: Dish){
        binding.titleTextView.text = dish.name
        binding.detailIngridient.text = dish.ingredients.map {it.name}?.joinToString(",")
        binding.viewPager.adapter = PhotoAdapter(this,dish.images)
        refreshShop(dish)
        binding.moin.setOnClickListener {
            itemCount = max(1, itemCount - 1)
            refreshShop(dish)
        }
        binding.plus.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.shopButton.setOnClickListener {
            addToBasket(dish, itemCount)
        }


    }
    private fun refreshShop(dish: Dish){
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.textCountItem.text = itemCount.toString()
        binding.shopButton.text = "Total: $price €"
        //addToBasket(dish, itemCount)
    }

    private fun refreshMenu(basket: Basket){
        val count = basket.itemsCount
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(NBR_ITEMS,count)
        editor.apply()
       // val itemsCount = basket.items.map{it.count}.reduce{acc, i -> acc +i}
        invalidateOptionsMenu()
    }

    private fun addToBasket(dish: Dish, count: Int) {
        val basket = Basket.getBasket(this)
        basket.ajoutItem(BasketItem(dish, count))
        basket.save(this)
        refreshMenu(basket)
        //val item = BasketItem(dish, count)
        val json = GsonBuilder().create().toJson(basket)
       // Log.d("Basket", json)
        Snackbar.make(binding.root,getString(R.string.basket_validation), Snackbar.LENGTH_LONG).show()
    }
}


