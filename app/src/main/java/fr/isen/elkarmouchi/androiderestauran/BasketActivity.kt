package fr.isen.elkarmouchi.androiderestauran
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.elkarmouchi.androiderestauran.basket.*
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityBasketBinding
import fr.isen.elkarmouchi.androiderestauran.detail.DetailViewFragment

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


}