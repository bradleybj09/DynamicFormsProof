package com.example.dynamicformsproof

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listeners = mutableListOf<AddViewListener>()

    var isLoginButtonVisible = MutableLiveData<Boolean>().also { it.value = true }
    var isSubmitButtonEnabled = MutableLiveData<Boolean>().also { it.value = true }

    private val repository = WorkFlowRepository()
    private lateinit var event: Event
    private val workFlows = mutableListOf<WorkFlow>()

    fun login() {
        isLoginButtonVisible.value = false
        setup()
    }

    fun submit() {
        val json = JSONObject().also { json ->
            workFlows.forEach { it.addToJson(json) }
        }
        repository.submitData(json)
    }

    fun addListener(listener: AddViewListener) = listeners.add(listener)

    fun removeListener(listener: AddViewListener) = listeners.remove(listener)

    private fun addWorkFlow(workflow: WorkFlow) {
        workFlows.add(workflow)
    }

    private fun setup() {
        event = repository.getWorkFlows().also { e ->
            e.workFlows.forEach { addWorkFlow(it) }
            listeners.forEach { it.onViewAdded(e) }
        }
    }

    fun getEvent(listener: AddViewListener) = listener.onViewAdded(event)
}