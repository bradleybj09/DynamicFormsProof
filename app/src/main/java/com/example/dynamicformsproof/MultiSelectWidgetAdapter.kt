package com.example.dynamicformsproof

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MultiSelectWidgetAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var choices = mutableListOf<SelectableItemChoice<T>>()

    fun setChoices(choices: List<T>) {
        this.choices = listOfSelectableItemChoices(choices)
    }

    fun getSelections(): List<T> {
        return choices.filter { it.isSelected }.map { it.item }
    }

    override fun getItemCount() = choices.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}

class SelectableItemChoice<T>(val item: T, val isSelected: Boolean)

fun <T> listOfSelectableItemChoices(data: List<T>): MutableList<SelectableItemChoice<T>> {
    return arrayListOf<SelectableItemChoice<T>>().also { list ->
        data.forEach { list.add(SelectableItemChoice(it, false)) }
    }
}