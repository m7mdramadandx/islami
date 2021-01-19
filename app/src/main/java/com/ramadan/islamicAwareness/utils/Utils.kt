package com.ramadan.islamicAwareness.utils

import android.content.Context
import com.ramadan.islamicAwareness.R.string
import de.blox.graphview.Node


class Utils(val context: Context) {
    val input = "abc"
    var array = Array(input.length) { input[it].toString() }


    val adam = Node(context.getString(string.adam))
    val empty = Node("\t\t\t\t\t\t\t\t")
    val arabized_arabs = Node(context.getString(string.arabized_arabs))
    val dashed = Node("--")
    val aron = Node(context.getString(string.aron))
    val abraham = Node(context.getString(string.abraham))
    val david = Node(context.getString(string.david))
    val dhul_kifl = Node(context.getString(string.dhul_kifl))
    val elisha = Node(context.getString(string.elisha))
    val elias = Node(context.getString(string.elias))
    val heber = Node(context.getString(string.heber))
    val idriss = Node(context.getString(string.idriss))
    val isaac = Node(context.getString(string.isaac))
    val ishmael = Node(context.getString(string.ishmael))
    val jacob = Node(context.getString(string.jacob))
    val jonah = Node(context.getString(string.jonah))
    val jesus = Node(context.getString(string.jesus))
    val jethri = Node(context.getString(string.jethri))
    val job = Node(context.getString(string.job))
    val joseph = Node(context.getString(string.joseph))
    val john = Node(context.getString(string.john))
    val lot = Node(context.getString(string.lot))
    val methuselah = Node(context.getString(string.methuselah))
    val moses = Node(context.getString(string.moses))
    val muhammad = Node(context.getString(string.muhammad))
    val noah = Node(context.getString(string.noah))
    val solomon = Node(context.getString(string.solomon))
    val zacharia = Node(context.getString(string.zacharia))


    val hashim_ibn_abd_manaf = Node(context.getString(string.hashim_ibn_abd_manaf))
    val abd_almuttalib = Node(context.getString(string.abd_almuttalib))
    val abd_allah = Node(context.getString(string.abd_allah))
    val aminah_bint_wahab = Node(context.getString(string.aminah_bint_wahab))
    val khadija = Node(context.getString(string.khadija))
    val sawada = Node(context.getString(string.sawada))
    val aisha = Node(context.getString(string.aisha))
    val zainab = Node(context.getString(string.zainab))
    val zainab1 = Node(context.getString(string.zainab))
    val zainab2 = Node(context.getString(string.zainab2))
    val hafsa = Node(context.getString(string.hafsa))
    val umm_salama = Node(context.getString(string.umm_salama))
    val juwayriya = Node(context.getString(string.juwayriya))
    val umm_habiba = Node(context.getString(string.umm_habiba))
    val safiyya = Node(context.getString(string.safiyya))
    val maymuma = Node(context.getString(string.maymuma))
    val maria = Node(context.getString(string.maria))
    val al_qasim = Node(context.getString(string.al_qasim))
    val fatima = Node(context.getString(string.fatima))
    val umm_kalthum = Node(context.getString(string.umm_kalthum))
    val ruqayyah = Node(context.getString(string.ruqayyah))
    val ali = Node(context.getString(string.ali))
    val umamah = Node(context.getString(string.umamah))
    val hussein = Node(context.getString(string.hussein))
    val hassan = Node(context.getString(string.hassan))
    val muhsin = Node(context.getString(string.muhsin))


    private val localeHelper = LocaleHelper()

}


