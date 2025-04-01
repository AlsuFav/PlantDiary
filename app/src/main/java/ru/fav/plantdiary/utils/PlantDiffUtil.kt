package ru.fav.plantdiary.utils

import androidx.recyclerview.widget.DiffUtil
import ru.fav.plantdiary.data.db.entities.PlantEntity

class PlantDiffUtil (
    private var oldList: List<PlantEntity>,
    private var newList: List<PlantEntity>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == (newList[newItemPosition]).id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}