package fr.isen.elkarmouchi.androiderestauran.Category
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.elkarmouchi.androiderestauran.R
import fr.isen.elkarmouchi.androiderestauran.databinding.DishesCellBinding
import fr.isen.elkarmouchi.androiderestauran.network.Dish

class CategoryAdapter(private val entries: List<Dish>,
                      private val entryClickListener:(Dish)-> Unit)
                        : RecyclerView.Adapter<CategoryAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(
            DishesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val dish =  entries[position]
        holder.layout.setOnClickListener{
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishesViewHolder(dishesBinding: DishesCellBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        val titleView: TextView = dishesBinding.dishesTitle
        val priceView: TextView = dishesBinding.dishPrice
        val imageView: ImageView = dishesBinding.dishimageView
        val layout = dishesBinding.root
        fun bind(dish: Dish){
            titleView.text = dish.name
            priceView.text = dish.prices.first().price + "€"
            var url: String? = null
            if(dish.images.isNotEmpty() && dish.images[0].isNotEmpty()){
                url = dish.images[0]
            }
            Picasso.get().load(url).placeholder(R.drawable.logo).into(imageView)
        }

    }
}