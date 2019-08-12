package com.example.dynamicformsproof

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformsproof.databinding.ItemMultiSelectBinding

class MultiSelectWidgetAdapter<T> : RecyclerView.Adapter<SelectableItemViewHolder>() {
    private var choices = mutableListOf<SelectableItemChoice<T>>()

    fun setChoices(choices: List<T>) {
        this.choices = listOfSelectableItemChoices(choices)
        notifyDataSetChanged()
    }

    fun setChoices(choices: List<T>, preSelect: List<Int>) {
        this.choices = listOfSelectableItemChoices(choices)
        preSelect.forEach {
            if (it < choices.size) {
                this.choices[it].isSelected.value = true
            }
        }
        notifyDataSetChanged()
    }

    fun setChoices(choices: List<T>, preSelect: List<Int>, noInteract: List<Int>) {
        this.choices = listOfSelectableItemChoices(choices)
        preSelect.forEach { i ->
            if (i < choices.size) {
                this.choices[i].isSelected.value = true
            }
        }
        noInteract.forEach { i ->
            if (i < choices.size) {
                Log.d("noInteract","index $i")
                this.choices[i]._isInteractible.value = false
                Log.d("noInteract", this.choices[i].itemString)
            }
        }
        notifyDataSetChanged()
    }

    fun setChoices(choices: List<T>, preSelect: List<Int>, indexesDisabled: List<Int>, indexesSelectDisabled: List<Int>, indexesDeselectDisabled: List<Int>) {
        this.choices = listOfSelectableItemChoices(choices)
        preSelect.forEach { i ->
            if (i < choices.size) {
                this.choices[i].isSelected.value = true
            }
        }
        indexesDisabled.forEach { i ->
            if (i < choices.size) {
                this.choices[i]._isInteractible.value = false
            }
        }
        indexesSelectDisabled.forEach { i ->
            if (i < choices.size && !preSelect.contains(i)) {
                Log.d("noSelect","index $i")
                this.choices[i]._isInteractible.value = false
                Log.d("noSelect", this.choices[i].itemString)
            }
        }
        indexesDeselectDisabled.forEach { i ->
            if (i < choices.size && preSelect.contains(i)) {
                Log.d("noDeselect","index $i")
                this.choices[i]._isInteractible.value = false
                Log.d("noDeselect", this.choices[i].itemString)
            }
        }
        notifyDataSetChanged()
    }

    fun getSelections(): List<T> {
        return choices.filter { it.isSelected.value == true }.map { it.item }
    }

    override fun getItemCount() = choices.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMultiSelectBinding.inflate(inflater, parent, false)
        return SelectableItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectableItemViewHolder, position: Int) {
        holder.bind(choices[position])
    }
}

class SelectableItemViewHolder(private val binding: ItemMultiSelectBinding) : RecyclerView.ViewHolder(binding.root) {
    fun <T> bind(item: SelectableItemChoice<T>) {
        binding.apply {
            this.item = item
            executePendingBindings()
        }
    }
}

data class SelectableItemChoice<T>(val item: T) {
    // expose MutableLiveData for 2-way data binding
    var isSelected = MutableLiveData<Boolean>().also { it.value = false }
    // expose read-only LiveData for 1-way data binding
    var _isInteractible = MutableLiveData<Boolean>().also { it.value = true }
    val isInteractible: LiveData<Boolean>
        get() = _isInteractible
    val itemString: String
        get() = item.toString()
}

fun <T> listOfSelectableItemChoices(data: List<T>): MutableList<SelectableItemChoice<T>> {
    return arrayListOf<SelectableItemChoice<T>>().also { list ->
        data.forEach { list.add(SelectableItemChoice(it)) }
    }
}