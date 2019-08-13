package com.example.dynamicformsproof

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamicformsproof.databinding.WidgetBooleanInputBinding
import com.example.dynamicformsproof.databinding.WidgetMultiSelectBinding
import com.example.dynamicformsproof.databinding.WidgetNumberInputBinding
import com.example.dynamicformsproof.databinding.WidgetTextInputBinding
import java.lang.RuntimeException

@BindingAdapter("radioOptions")
fun setRadioChoices(view: View, choices: List<String>) {

}

@BindingAdapter("workFlow")
fun addWidgets(container: ConstraintLayout, workFlow: WorkFlow) {
    val layoutInflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val views = mutableListOf<View>()
    val widgets = workFlow.formWidgets
    widgets.forEach { data ->
        val newView = when (data) {
            is BooleanInputFormWidget -> WidgetBooleanInputBinding.inflate(
                layoutInflater,
                container,
                false
            ).also { it.data = data }.root
            is TextInputFormWidget -> WidgetTextInputBinding.inflate(
                layoutInflater,
                container,
                false
            ).also { it.data = data }.root
            is NumberInputFormWidget -> WidgetNumberInputBinding.inflate(
                layoutInflater,
                container,
                false
            ).also { it.data = data }.root
            is MultiSelectFormWidget -> WidgetMultiSelectBinding.inflate(
                layoutInflater,
                container,
                false
            ).also {
                it.data = data
                it.widgetRecyclerView.layoutManager = if (data.maxColumns == 1) {
                    LinearLayoutManager(container.context)
                } else {
                    GridLayoutManager(container.context, data.maxColumns)
                }
            }.root

            else -> throw RuntimeException()
        }
        views.add(newView)
    }
    views.forEach { widgetView ->
        widgetView.id = ViewCompat.generateViewId()
        container.addView(widgetView)
    }
    val constraintSet = ConstraintSet().also { it.clone(container) }

    lateinit var previous: View
    for (i in 0 until views.size) {
        if (i == 0) {
            constraintSet.connect(views[i].id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        } else {
            constraintSet.connect(views[i].id, ConstraintSet.TOP, previous.id, ConstraintSet.BOTTOM)
        }
        previous = views[i]
    }
    constraintSet.applyTo(container)
}