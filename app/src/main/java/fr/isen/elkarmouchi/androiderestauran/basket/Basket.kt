package fr.isen.elkarmouchi.androiderestauran.basket

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.elkarmouchi.androiderestauran.detail.DetailActivity
//import fr.isen.elkarmouchi.androiderestauran.detail.DetailActivity.Companion.NBR_ITEMS
//import fr.isen.elkarmouchi.androiderestauran.detail.DetailActivity.Companion.USER_PREFERENCES_NAME
import fr.isen.elkarmouchi.androiderestauran.network.Dish
import java.io.File
import java.io.Serializable

class Basket (val items: MutableList<BasketItem>): Serializable {
    var itemsCount: Int = 0
        get() {
            return if (items.count() > 0) {
                items.map { it.count }.reduce { acc, i -> acc + i }

        }else {
             0
            }
}

    fun ajoutItem(item: BasketItem){
        val itemExist= items.firstOrNull(){
            it.dish.name ==  item.dish.name
        }
        itemExist?.let{
            itemExist.count = item.count
        }?: run{
            items.add(item)
        }

    }
    fun clear() {
        items.clear()
    }
    fun save(context: Context){
        val jsFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsFile.writeText(GsonBuilder().create().toJson(this))
        updateCounter(context)

    }
    private fun updateCounter(context: Context) {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(NBR_ITEMS,itemsCount)
        editor.apply()
    }


    companion object{
       fun getBasket(context: Context): Basket{
           val jsFile= File(context.cacheDir.absolutePath + BASKET_FILE)
           return if(jsFile.exists()) {
               val json = jsFile.readText()
               GsonBuilder().create().fromJson(json, Basket::class.java)
           } else {
               Basket(mutableListOf())
           }
       }
        const val BASKET_FILE = "basket.json"
        const val NBR_ITEMS = "NBR_ITEMS"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}
class BasketItem(val dish: Dish, var count: Int): Serializable {}
