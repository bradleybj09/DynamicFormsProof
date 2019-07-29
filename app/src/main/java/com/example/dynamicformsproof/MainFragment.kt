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

    override fun onViewAdded(event: Event) {
        Log.d("fragment", "onViewAdded called with event: $event")
        val rootView = view?.rootView as ViewGroup
        val views = mutableListOf<View>()
        val workFlows = event.workFlows
        workFlows.forEach { data ->
            val newView = when (data) {
                is BooleanInputWorkFlow -> WorkFlowBooleanInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                is TextInputWorkFlow -> WorkFlowTextInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                is NumberInputWorkFlow -> WorkFlowNumberInputBinding.inflate(
                    layoutInflater,
                    rootView,
                    false
                ).also { it.data = data }.root
                else -> throw RuntimeException()
            }
            views.add(newView)
        }
        views.forEach { workFlowView ->
            workFlowView.id = generateViewId()
            container.addView(workFlowView)
        }
        val constraintSet = ConstraintSet().also { it.clone(container) }

        lateinit var previous: View
        for (i in 0 until views.size) {
//            val lastView = i == views.size - 1
            if (i == 0) {
                constraintSet.connect(views[i].id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                constraintSet.connect(views[i].id, ConstraintSet.TOP, previous.id, ConstraintSet.BOTTOM)
            }
//            if (lastView) {
//                constraintSet.connect(views[i].id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
//            }
            previous = views[i]
        }

        val viewIds = views.map { it.id }

//        constraintSet.createVerticalChain(ConstraintSet.PARENT_ID, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, viewIds.toIntArray(), null, ConstraintSet.CHAIN_PACKED)
        constraintSet.applyTo(container)
    }
}