package com.ramadan.islami.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import com.ramadan.islami.`data`.model.Surah
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class AyahPageArgs(
  public val surah: Surah? = null
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(Surah::class.java)) {
      result.putParcelable("surah", this.surah as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(Surah::class.java)) {
      result.putSerializable("surah", this.surah as Serializable?)
    }
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AyahPageArgs {
      bundle.setClassLoader(AyahPageArgs::class.java.classLoader)
      val __surah : Surah?
      if (bundle.containsKey("surah")) {
        if (Parcelable::class.java.isAssignableFrom(Surah::class.java) ||
            Serializable::class.java.isAssignableFrom(Surah::class.java)) {
          __surah = bundle.get("surah") as Surah?
        } else {
          throw UnsupportedOperationException(Surah::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __surah = null
      }
      return AyahPageArgs(__surah)
    }
  }
}
