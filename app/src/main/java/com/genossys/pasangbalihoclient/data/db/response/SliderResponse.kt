package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Slider

data class SliderResponse(
    val respon: String?,
    val message: String?,
    val slider: List<Slider>
)