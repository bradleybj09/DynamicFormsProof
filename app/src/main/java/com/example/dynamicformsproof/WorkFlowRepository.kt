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
            "    }\n" +
            "  ]\n" +
            "}"

    fun submitData(json: JSONObject) {
        Log.d("requestBody", "$json")
    }

    fun getWorkFlow(): WorkFlow {
        val moshi =  WorkFlowMoshiProvider().getMoshi()
        val adapter = moshi.adapter(WorkFlow::class.java)
        return adapter.fromJson(string) ?: WorkFlow.EMPTY_EVENT
    }
}