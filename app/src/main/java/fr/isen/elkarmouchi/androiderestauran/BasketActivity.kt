package fr.isen.elkarmouchi.androiderestauran
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.elkarmouchi.androiderestauran.basket.*
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityBasketBinding
import fr.isen.elkarmouchi.androiderestauran.detail.DetailViewFragment
import fr.isen.elkarmouchi.androiderestauran.register.RegisterActivity

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
        if(requestCode == RegisterActivity.REQUEST_CODE) {
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
    }


}