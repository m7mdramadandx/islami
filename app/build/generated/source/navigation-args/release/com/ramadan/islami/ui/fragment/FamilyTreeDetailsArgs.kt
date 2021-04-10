package com.ramadan.islami.ui.fragment

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FamilyTreeDetailsArgs(
  public val intentKey: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("intentKey", this.intentKey)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FamilyTreeDetailsArgs {
      bundle.setClassLoader(FamilyTreeDetailsArgs::class.java.classLoader)
      val __intentKey : String?
      if (bundle.containsKey("intentKey")) {
        __intentKey = bundle.getString("intentKey")
        if (__intentKey == null) {
          throw IllegalArgumentException("Argument \"intentKey\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"intentKey\" is missing and does not have an android:defaultValue")
      }
      return FamilyTreeDetailsArgs(__intentKey)
    }
  }
}
