package com.example.dynamicformsproof

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class WorkFlowMoshiProvider {

    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(FormWidget::class.java, "widgetType")
                .withSubtype(TextInputFormWidget::class.java, "TextInputFormWidget")
                .withSubtype(NumberInputFormWidget::class.java, "NumberInputFormWidget")
                .withSubtype(BooleanInputFormWidget::class.java, "BooleanInputFormWidget")
                .withSubtype(SingleSelectFormWidget::class.java, "SingleSelectFormWidget")
                .withSubtype(MultiSelectFormWidget::class.java, "MultiSelectFormWidget"))
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}