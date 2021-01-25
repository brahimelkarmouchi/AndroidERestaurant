package fr.isen.elkarmouchi.androiderestauran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button2.setOnClickListener {
            statCategoryActivity(ItemType.STARTER)
           // redirectToCategory("Entrées")
            //Toast.makeText(this, getString(R.string.starter),Toast.LENGTH_SHORT).show()

        }
        binding.button3.setOnClickListener {
             statCategoryActivity(ItemType.MAIN)
           // redirectToCategory("Plats")

           //Toast.makeText(this, getString(R.string.main),Toast.LENGTH_SHORT).show()

        }
        binding.button4.setOnClickListener {

            statCategoryActivity(ItemType.DESSERT)
          //  redirectToCategory("Desserts")
           // Toast.makeText(this, getString(R.string.deserts),Toast.LENGTH_SHORT).show()

        }



    }
    /*private fun redirectToCategory(@StringRes categoryRes: Int) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, getString(categoryRes))
        startActivity(intent)
    }*/

    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }


    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

}