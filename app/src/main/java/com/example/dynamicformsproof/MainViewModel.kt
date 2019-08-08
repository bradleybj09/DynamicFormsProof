package com.example.dynamicformsproof

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listeners = mutableListOf<AddViewListener>()

    var isLoginButtonVisible = MutableLiveData<Boolean>().also { it.value = true }
    var isSubmitButtonEnabled = MutableLiveData<Boolean>().also { it.value = true }

    private val repository = WorkFlowRepository()
    private lateinit var workFlow: WorkFlow
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

    private fun addWorkFlow(workflow: FormWidget) {
        widgets.add(workflow)
    }

    private fun setup() {
        workFlow = repository.getWorkFlow().also { w ->
            w.formWidgets.forEach { addWorkFlow(it) }
            listeners.forEach { it.onViewAdded(w) }
        }
    }
}