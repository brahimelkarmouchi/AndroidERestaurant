package fr.isen.elkarmouchi.androiderestauran

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.elkarmouchi.androiderestauran.basket.Basket

open class BaseActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.basket)?.actionView
        val countText = menuView?.findViewById(R.id.countItem) as? TextView
        val count = getItemsCount()
        countText?.visibility = View.VISIBLE

        countText?.text = count.toString()
        menuView?.setOnClickListener{
          /*  val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
           */
            if(count > 0) {
                val intent = Intent(this, BasketActivity::class.java)
                startActivity(intent)
                          }

          //  Log.d("basket", "start activity")
        }
        return true
    }

    private fun getItemsCount(): Int{
        val sharedPreferences = getSharedPreferences(Basket.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(Basket.NBR_ITEMS, 0)
    }

    override fun onResume(){
        super.onResume()
        invalidateOptionsMenu()
    }
}