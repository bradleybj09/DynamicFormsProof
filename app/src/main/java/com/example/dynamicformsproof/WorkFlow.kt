package com.example.dynamicformsproof

data class WorkFlow(val name: String, val formWidgets: List<FormWidget>) {

    companion object {
        val EMPTY_EVENT = WorkFlow("empty event", listOf())
    }
}