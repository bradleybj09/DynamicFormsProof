package com.example.dynamicformsproof

data class Event(val name: String, val workFlows: List<WorkFlow>) {

    companion object {
        val EMPTY_EVENT = Event("empty event", listOf())
    }
}