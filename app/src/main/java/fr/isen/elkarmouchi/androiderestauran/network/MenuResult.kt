package fr.isen.elkarmouchi.androiderestauran.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MenuResult(val data: List<Category>) {}

class Category (@SerializedName("name_fr")val name:String,val items:List<Dish>){}

class Dish(
    @SerializedName("name_fr") val name:String,
    val images:List<String>,
    val ingredients:List<Ingredient>,
    val prices:List<Price>):Serializable{}

class Ingredient(@SerializedName("name_fr")val name:String):Serializable{}
class Price(val price:String):Serializable