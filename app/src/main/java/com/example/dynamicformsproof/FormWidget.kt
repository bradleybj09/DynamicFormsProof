package com.example.dynamicformsproof

import org.json.JSONObject

enum class WidgetType {
    TextInput,
    NumberInput,
    BooleanInput,
    SingleSelect,
    MultiSelect
}

sealed class FormWidget(widgetType: WidgetType) {
    abstract fun addToJson(json: JSONObject)
    abstract fun isResponseValid(): Boolean
    abstract fun isWarningRequired(): Boolean
    abstract val invalidString: String
    abstract val warningString: String
}

/** We could have a special ConsigneeWorkFlow that would implement saving and prefilling to following ConsigneeWorkFlow objects,
 * but is unlikely that we would have dynamic prefilling, at least for now. It wouldn't be unreasonable to have some
 * kind of id that goes along with these, and then you could pull the current data from one id to prefill another.
 * Prefilling is still fine, but from the start, as part of the data payload.
 */
data class TextInputFormWidget(
    var key: String,
    var minLength: Int,
    var maxLength: Int,
    var required: Boolean,
    var preFill: String,
    var hint: String,
    override val invalidString: String,
    override val warningString: String
) : FormWidget(WidgetType.TextInput) {
    var text = preFill
    override fun addToJson(json: JSONObject) = Unit.also { json.put(key, text) }
    override fun isResponseValid() =  !required || text.length in minLength..maxLength
    override fun isWarningRequired() = false
}

data class NumberInputFormWidget(
    var preFill: Double,
    var requiredMinimum: Double,
    var requiredMaximum: Double,
    var warnMinimum: Double,
    var warnMaximum: Double,
    var key: String,
    var hint: String,
    var required: Boolean,
    override val invalidString: String,
    override val warningString: String
) : FormWidget(WidgetType.NumberInput) {
    var text = preFill.toString()
    private val number: Double
        get() = text.toDouble()
    override fun addToJson(json: JSONObject) = Unit.also { json.put(key, number) }
    override fun isResponseValid() = !required || number in requiredMinimum..requiredMaximum
    override fun isWarningRequired() = number !in warnMinimum..warnMaximum
}

data class BooleanInputFormWidget(
    var preFill: Boolean,
    var trueRequired: Boolean,
    var key: String,
    var hint: String,
    override val invalidString: String,
    override val warningString: String
) : FormWidget(WidgetType.BooleanInput) {
    var booleanInput = preFill
    override fun addToJson(json: JSONObject) = Unit.also { json.put(key, booleanInput) }
    override fun isResponseValid() = !trueRequired || booleanInput
    override fun isWarningRequired() = false
}

data class SingleSelectFormWidget(
    var preSelect: Int?,
    var key: String,
    var choices: Array<String>,
    var required: Boolean,
    override val invalidString: String,
    override val warningString: String
) : FormWidget(WidgetType.SingleSelect) {
    private val noSelection = "NO_SELECTION_MADE"
    var selection = preSelect?.let { choices[it] } ?: noSelection
    override fun addToJson(json: JSONObject) = Unit.also { json.put(key, selection) }
    override fun isResponseValid() = !required || selection != noSelection
    override fun isWarningRequired() = false
}

data class MultiSelectFormWidget(
    var preSelect: Array<Int>,
    var maxColumns: Int,
    var key: String,
    var choices: MutableList<String>,
    var minChoices: Int,
    var maxChoices: Int,
    override val invalidString: String,
    override val warningString: String
) : FormWidget(WidgetType.MultiSelect) {
    val adapter = MultiSelectWidgetAdapter<String>()
    var preSelects = choices.filterIndexed { i, _ -> i in preSelect }
    val selections: List<String>
        get() = adapter.getSelections()
    override fun addToJson(json: JSONObject) = Unit.also { json.put(key, adapter.getSelections()) }
    override fun isResponseValid() = selections.size in minChoices..maxChoices
    override fun isWarningRequired() = false
}

