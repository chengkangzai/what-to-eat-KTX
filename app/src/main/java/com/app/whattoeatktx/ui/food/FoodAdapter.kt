package com.app.whattoeatktx.ui.food

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.whattoeatktx.R
import com.app.whattoeatktx.model.Food

class FoodAdapter(var context: Context, var string: Array<String>, var food: MutableList<Food>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.myText.text = string[position]
        val imgSrc =
            "https://ui-avatars.com/api/?name=${string[position]}&rounded=true&background=random&size=200"

        //https://www.appsdeveloperblog.com/image-from-remote-url-kotlin-glide/
        Glide.with(context).load(imgSrc).into(holder.myImageView)

        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, ViewFoodActivity::class.java)
            intent.putExtra("foodName", food[position].food)
            intent.putExtra("timestamp", food[position].timestamp)
            intent.putExtra("imgSrc", imgSrc)
            intent.putExtra("foodId", food[position].id)
            intent.putExtra("tags", food[position].tags)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return string.size
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myText: TextView = itemView.findViewById(R.id.myText1)
        val myImageView: ImageView = itemView.findViewById(R.id.myImageView)
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.row_food)
    }
}
