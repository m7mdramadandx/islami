package com.ramadan.islami.utils

import com.ramadan.islami.utils.ResStatus.*


data class Resource<out T>(val resStatus: ResStatus, val res_data: T?, val res_message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(resStatus = SUCCESS, res_data = data, res_message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(resStatus = ERROR, res_data = data, res_message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(resStatus = LOADING, res_data = data, res_message = null)
    }
}