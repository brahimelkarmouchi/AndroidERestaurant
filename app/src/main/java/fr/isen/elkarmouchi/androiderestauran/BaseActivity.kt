package fr.isen.elkarmouchi.androiderestauran

import android.content.Context
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.elkarmouchi.androiderestauran.detail.DetailActivity
import androidx.core.view.isVisible

open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.basket)?.actionView
        val countText = menuView?.findViewById(R.id.countItem) as? TextView
        val count = getItemsCount()
        countText?.visibility = View.VISIBLE

        countText?.text = count.toString()
        menuView?.setOnClickListener{
            Log.d("basket", "start activity")
        }
        return true
    }

    private fun getItemsCount(): Int{
        val sharedPreferences = getSharedPreferences(DetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DetailActivity.NBR_ITEMS, 0)
    }

    override fun onResume(){
        super.onResume()
        invalidateOptionsMenu()
    }
}