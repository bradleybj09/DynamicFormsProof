package com.example.dynamicformsproof

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class WorkFlowMoshiProvider {

    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(WorkFlow::class.java, "workFlowType")
                .withSubtype(TextInputWorkFlow::class.java, "TextInputWorkFlow")
                .withSubtype(NumberInputWorkFlow::class.java, "NumberInputWorkFlow")
                .withSubtype(BooleanInputWorkFlow::class.java, "BooleanInputWorkFlow")
                .withSubtype(SingleSelectWorkFlow::class.java, "SingleSelectWorkFlow")
                .withSubtype(MultiSelectWorkFlow::class.java, "MultiSelectWorkFlow"))
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}