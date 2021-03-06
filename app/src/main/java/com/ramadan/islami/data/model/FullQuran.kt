package com.ramadan.islami.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FullQuran {
    @SerializedName("code")
    @Expose
    var code = 0

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("data")
    @Expose
    private var data: Data? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param status
     * @param data
     * @param code
     */
    constructor(
        code: Int,
        status: String?,
        data: Data?,
    ) : super() {
        this.code = code
        this.status = status
        this.data = data
    }

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }
}