package fr.isen.elkarmouchi.androiderestauran.basket

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.elkarmouchi.androiderestauran.network.Dish
import java.io.File
import java.io.Serializable

class Basket (val items: MutableList<BasketItem>): Serializable {

    fun ajoutItem(item: BasketItem){
        val itemExist= items.firstOrNull(){
            it.dish.name ==  it.dish.name
        }
        itemExist?.let{
            itemExist.count += item.count
        }?: run{
            items.add(item)
        }

    }
    fun save(context: Context){
        val jsFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsFile.writeText(GsonBuilder().create().toJson(this))

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
    }
}
class BasketItem(val dish: Dish, var count: Int): Serializable {}
