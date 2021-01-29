package fr.isen.elkarmouchi.androiderestauran.basket

import fr.isen.elkarmouchi.androiderestauran.network.Dish
import java.io.Serializable

class Basket (val items: List<BasketItem>): Serializable {}
class BasketItem(val dish: Dish, val count: Int): Serializable {}
