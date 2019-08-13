package com.example.dynamicformsproof

import android.util.Log
import org.intellij.lang.annotations.Language
import org.json.JSONObject

class WorkFlowRepository {

    @Language(value = "json")
    val string = """{
        "name": "deliveryWorkFlow",
        "formWidgets": [
            {
              "widgetType": "TextInputFormWidget",
              "key": "signerName",
              "minLength": 0,
              "maxLength": 99999,
              "required": false,
              "preFill": "",
              "hint": "Signer Name",
              "invalidString": "Please enter signer name",
              "warningString": ""
            },
            {
              "widgetType": "NumberInputFormWidget",
              "preFill": 0,
              "requiredMinimum": 50,
              "requiredMaximum": 100000,
              "warnMinimum": 5000,
              "warnMaximum": 50000,
              "key": "pounds",
              "hint": "Weight(lbs)",
              "required": true,
              "invalidString": "Please enter a value for weight",
              "warningString": "Weight entry looks fishy, please confirm it is correct and in POUNDS"
            },
            {
              "widgetType": "BooleanInputFormWidget",
              "preFill": false,
              "trueRequired": true,
              "key": "checkedThing",
              "hint": "Thing checked",
              "invalidString": "You cannot continue without marking that you have checked the thing",
              "warningString": ""
            },
            {
              "widgetType": "MultiSelectFormWidget",
              "maxColumns": 3,
              "preSelect": [
                2,
                4
              ],
              "indexesDisabled": [],
              "indexesSelectDisabled": [
                1,
                2
              ],
              "indexesDeselectDisabled": [
                4,
                5
              ],
              "key": "multipleThings",
              "hint": "Many things",
              "choices": [
                "Thing 0",
                "Thing 1",
                "Thing 2",
                "Thing 3",
                "Thing 4",
                "Thing 5",
                "Thing 6",
                "Thing 7"
              ],
              "minChoices": 3,
              "maxChoices": 5,
              "invalidString": "You must select between 3 and 5 things",
              "warningString": ""
            }
        ]
    }"""

    fun submitData(json: JSONObject) {
        Log.d("requestBody", "$json")
    }

    fun getWorkFlow(): WorkFlow {
        val moshi =  WorkFlowMoshiProvider().getMoshi()
        val adapter = moshi.adapter(WorkFlow::class.java)
        return adapter.fromJson(string) ?: WorkFlow.EMPTY_WORKFLOW
    }
}