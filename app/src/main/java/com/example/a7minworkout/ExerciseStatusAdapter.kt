package com.example.a7minworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>)
    :RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
                                    // xml file to bind
        inner class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
       val tvItem = binding.tvItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val  model : ExerciseModel = items[position]
        holder.tvItem.text= model.getId().toString()


        when {
            model.getIsSelected()->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_thin_border_background)
                holder.tvItem.setTextColor(Color.parseColor("#FF000000"))

            }
            model.getIsCompleted()->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_accent_color_background)
                holder.tvItem.setTextColor(Color.parseColor("#FF000000"))

            }else->{
            holder.tvItem.background=
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_grey_background)
            holder.tvItem.setTextColor(Color.parseColor("#FF000000"))

            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}