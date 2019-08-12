package com.example.dynamicformsproof

import android.util.Log
import org.json.JSONObject

class WorkFlowRepository {

    val string = "{\n" +
            "  \"name\": \"deliveryWorkFlow\",\n" +
            "  \"formWidgets\": [\n" +
            "    {\n" +
            "      \"widgetType\": \"TextInputFormWidget\",\n" +
            "      \"key\": \"signerName\",\n" +
            "      \"minLength\": 0,\n" +
            "      \"maxLength\": 99999,\n" +
            "      \"required\": false,\n" +
            "      \"preFill\": \"\",\n" +
            "      \"hint\": \"Signer Name\",\n" +
            "      \"invalidString\": \"Please enter signer name\",\n" +
            "      \"warningString\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"widgetType\": \"NumberInputFormWidget\",\n" +
            "      \"preFill\": 0,\n" +
            "      \"requiredMinimum\": 50,\n" +
            "      \"requiredMaximum\": 100000,\n" +
            "      \"warnMinimum\": 5000,\n" +
            "      \"warnMaximum\": 50000,\n" +
            "      \"key\": \"pounds\",\n" +
            "      \"hint\": \"Weight(lbs)\",\n" +
            "      \"required\": true,\n" +
            "      \"invalidString\": \"Please enter a value for weight\",\n" +
            "      \"warningString\": \"Weight entry looks fishy, please confirm it is correct and in POUNDS\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"widgetType\": \"BooleanInputFormWidget\",\n" +
            "      \"preFill\": false,\n" +
            "      \"trueRequired\": true,\n" +
            "      \"key\": \"checkedThing\",\n" +
            "      \"hint\": \"Thing checked\",\n" +
            "      \"invalidString\": \"You cannot continue without marking that you have checked the thing\",\n" +
            "      \"warningString\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"widgetType\": \"MultiSelectFormWidget\",\n" +
            "      \"maxColumns\": 3,\n" +
            "      \"preSelect\": [\n" +
            "        2,\n" +
            "        4\n" +
            "      ],\n" +
            "      \"indexesDisabled\": [],\n" +
            "      \"indexesSelectDisabled\": [\n" +
            "        1,\n" +
            "        2\n" +
            "      ],\n" +
            "      \"indexesDeselectDisabled\": [\n" +
            "        4,\n" +
            "        5\n" +
            "      ],\n" +
            "      \"key\": \"multipleThings\",\n" +
            "      \"hint\": \"Many things\",\n" +
            "      \"choices\": [\n" +
            "        \"Thing 0\",\n" +
            "        \"Thing 1\",\n" +
            "        \"Thing 2\",\n" +
            "        \"Thing 3\",\n" +
            "        \"Thing 4\",\n" +
            "        \"Thing 5\",\n" +
            "        \"Thing 6\",\n" +
            "        \"Thing 7\"\n" +
            "      ],\n" +
            "      \"minChoices\": 3,\n" +
            "      \"maxChoices\": 5,\n" +
            "      \"invalidString\": \"You must select between 3 and 5 things\",\n" +
            "      \"warningString\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    fun submitData(json: JSONObject) {
        Log.d("requestBody", "$json")
    }

    fun getWorkFlow(): WorkFlow {
        val moshi =  WorkFlowMoshiProvider().getMoshi()
        val adapter = moshi.adapter(WorkFlow::class.java)
        return adapter.fromJson(string) ?: WorkFlow.EMPTY_WORKFLOW
    }
}