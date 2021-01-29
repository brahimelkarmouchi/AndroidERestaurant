package fr.isen.elkarmouchi.androiderestauran.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityDetailBinding
import fr.isen.elkarmouchi.androiderestauran.network.Dish
import kotlin.math.max

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DISH = "EXTRA_DISH"
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

    }
    private fun refreshShop(dish: Dish){
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.textCountItem.text = itemCount.toString()
        binding.shopButton.text = "Total: $price €"
    }
}

