package ru.fav.plantdiary.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.fav.plantdiary.R
import ru.fav.plantdiary.data.db.converters.PhotoConverter
import ru.fav.plantdiary.data.db.entities.PlantEntity
import ru.fav.plantdiary.databinding.ItemPlantBinding
import ru.fav.plantdiary.utils.PlantDiffUtil
import java.text.NumberFormat

class PlantsAdapter (
    private val onPlantClick: (PlantEntity) -> Unit
) : RecyclerView.Adapter<PlantsAdapter.PlantViewHolder>() {

    private var plants = mutableListOf<PlantEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun getItemCount(): Int = plants.size

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plants[position])
    }

    inner class PlantViewHolder(private val binding: ItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: PlantEntity) {
            binding.textViewPlantName.text = plant.name
            val numberFormat = NumberFormat.getIntegerInstance().apply {
                isGroupingUsed = false
            }
            binding.textViewPlantingYear.text = numberFormat.format(plant.plantingYear)

            plant.photo?.let { byteArray ->
                val bitmap = PhotoConverter().toBitmap(byteArray)
                binding.imageViewPlantPhoto.setImageBitmap(bitmap)
            } ?: run {
                binding.imageViewPlantPhoto.setImageResource(R.drawable.ic_plant)
            }

            itemView.setOnClickListener {
                onPlantClick(plant)
            }
        }
    }

    fun updateData(list: MutableList<PlantEntity>) {
        val diff = PlantDiffUtil(oldList = plants, newList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        plants.clear()
        plants.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(wish: PlantEntity): Int? {
        val position = plants.indexOfFirst { it.id == wish.id }
        if (position != -1) {
            plants.removeAt(position)
            return position
        }
        return null
    }

    fun getItem(position: Int): PlantEntity? {
        return plants.getOrNull(position)
    }
}
