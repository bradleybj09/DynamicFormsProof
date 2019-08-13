package com.example.dynamicformsproof

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class MainViewModel : ViewModel(), ValidationListener {

    private val listeners = mutableListOf<AddViewListener>()

    var isLoginButtonVisible = MutableLiveData<Boolean>().also { it.value = true }
    var isSubmitButtonEnabled = MutableLiveData<Boolean>().also { it.value = true }
    private var _workflow = MutableLiveData<WorkFlow>().also { it.value = WorkFlow.EMPTY_WORKFLOW }
    val workFlow: LiveData<WorkFlow> =_workflow

    private val repository = WorkFlowRepository()
    private val widgets = mutableListOf<FormWidget>()

    fun login() {
        isLoginButtonVisible.value = false
        setup()
    }

    fun submit() {
        val invalidWorkFlows = widgets.filter { !it.isResponseValid() }
        if (invalidWorkFlows.isNotEmpty()) {
            Log.d("invalid","first error: ${invalidWorkFlows.first().invalidString}")
            return
        }
        val json = JSONObject().also { json ->
            widgets.forEach { it.addToJson(json) }
        }
        repository.submitData(json)
    }

    fun addListener(listener: AddViewListener) = listeners.add(listener)

    fun removeListener(listener: AddViewListener) = listeners.remove(listener)

    private fun addWorkFlow(widget: FormWidget) {
        widget.validationListener = this
        widgets.add(widget)
    }

    override fun checkValidation() {
        val valid = widgets.all { it.isResponseValid() }
        Log.d("is valid:", "$valid")
    }

    private fun setup() {
        repository.getWorkFlow().also { w ->
            w.formWidgets.forEach { addWorkFlow(it) }
            _workflow.value = w
        //    listeners.forEach { it.onViewAdded(w) }

        }
    }
}