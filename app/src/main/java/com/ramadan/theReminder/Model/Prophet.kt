package com.ramadan.theReminder.Model


class Prophet(name: String, story: String, imgUrl: String) {
    var name: String? = name
    var story: String? = story
    var imgUrl: String? = imgUrl

    fun name(): String? {
        return name
    }

    fun story(): String? {
        return story
    }

    fun imgUrl(): String? {
        return imgUrl
    }


}
