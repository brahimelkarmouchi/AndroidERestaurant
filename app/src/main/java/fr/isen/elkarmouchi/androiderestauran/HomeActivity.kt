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

            Toast.makeText(this, getString(R.string.starter),Toast.LENGTH_SHORT).show()

        }
        binding.button3.setOnClickListener {

            Toast.makeText(this, getString(R.string.main),Toast.LENGTH_SHORT).show()

        }
        binding.button4.setOnClickListener {

            Toast.makeText(this, getString(R.string.deserts),Toast.LENGTH_SHORT).show()

        }
          
    }

}