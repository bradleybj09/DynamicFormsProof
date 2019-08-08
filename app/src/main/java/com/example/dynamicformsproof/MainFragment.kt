package com.example.dynamicformsproof

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat.generateViewId
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.dynamicformsproof.databinding.*
import java.lang.RuntimeException

class MainFragment : Fragment(), AddViewListener {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMainBinding
    private lateinit var container: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        this.container = binding.mainContainerLayout
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.addListener(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeListener(this)
    }

    override fun onViewAdded(workFlow: WorkFlow) {
        Log.d("fragment", "onViewAdded called with workFlow: $workFlow")
        val rootView = view?.rootView as ViewGroup
        val views = mutableListOf<View>()
        val widgets = workFlow.formWidgets
        widgets.forEach { data ->
            val newView = when (data) {
                is BooleanInputFormWidget -> WorkFlowBooleanInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                is TextInputFormWidget -> WorkFlowTextInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                is NumberInputFormWidget -> WorkFlowNumberInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                else -> throw RuntimeException()
            }
            views.add(newView)
        }
        views.forEach { widgetView ->
            widgetView.id = generateViewId()
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
}