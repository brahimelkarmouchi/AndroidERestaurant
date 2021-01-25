package fr.isen.elkarmouchi.androiderestauran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log
import android.widget.Toast
import fr.isen.elkarmouchi.androiderestauran.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.setOnClickListener {
            val starter = "Vous avez choisez l'entrée!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, starter, duration)
            toast.show()

            //Toast.makeText(this, getString(R.string.toast))
        }
            binding.button3.setOnClickListener {
                val main = "Vous avez choisez les plats"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, main, duration)
                toast.show()
               // Log.d("button","main")
        }
        binding.button4.setOnClickListener {
            val deserts = "Vous avez choisez les déserts"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, deserts, duration)
            toast.show()
            // Log.d("button","main")
        }
    }

}