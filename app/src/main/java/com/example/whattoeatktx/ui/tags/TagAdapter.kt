package com.example.whattoeatktx.ui.tags

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
import com.example.whattoeatktx.R
import com.example.whattoeatktx.model.Tag

class TagAdapter(var context: Context, var string: Array<String>, var tags: MutableList<Tag>) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.myText.text = string[position]
        val imgSrc =
            "https://ui-avatars.com/api/?name=${string[position]}&rounded=true&background=random&size=200"
//
        //https://www.appsdeveloperblog.com/image-from-remote-url-kotlin-glide/
        Glide.with(context).load(imgSrc).into(holder.myImageView)

        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, ViewTagActivity::class.java)
            intent.putExtra("tagName", tags[position].name)
            intent.putExtra("tagId", tags[position].id)
            intent.putExtra("imageSrc",imgSrc)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return string.size
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myText: TextView = itemView.findViewById(R.id.rowTag_tagName)
        val myImageView: ImageView = itemView.findViewById(R.id.rowTag_img)
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.row_tag)
    }
}
