package com.ramadan.islamicAwareness.Model


class Prophet(
    name: String, imgUrl: String, section1: String,
    section2: String, section3: String, section4: String,
    section5: String, section6: String, section7: String
) {
    var name: String? = name
    var imgUrl: String? = imgUrl
    var section1: String? = section1
    var section2: String? = section2
    var section3: String? = section3
    var section4: String? = section4
    var section5: String? = section5
    var section6: String? = section6
    var section7: String? = section7

    fun name(): String? {
        return name
    }

    fun imgUrl(): String? {
        return imgUrl
    }


}
