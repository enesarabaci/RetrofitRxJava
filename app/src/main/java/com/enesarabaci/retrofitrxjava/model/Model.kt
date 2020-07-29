package com.enesarabaci.retrofitrxjava.model

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("mission_name")
    var mission : String,
    @SerializedName("flight_number")
    var flight : String
)