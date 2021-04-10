package com.ramadan.islami.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.ramadan.islami.R
import com.ramadan.islami.`data`.model.Surah
import java.io.Serializable
import kotlin.Int
import kotlin.Suppress

public class SurahDirections private constructor() {
  private data class ActionQuranToAyahFragment(
    public val surah: Surah? = null
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_quran_to_ayah_fragment

    @Suppress("CAST_NEVER_SUCCEEDS")
    public override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Surah::class.java)) {
        result.putParcelable("surah", this.surah as Parcelable?)
      } else if (Serializable::class.java.isAssignableFrom(Surah::class.java)) {
        result.putSerializable("surah", this.surah as Serializable?)
      }
      return result
    }
  }

  public companion object {
    public fun actionQuranToAyahFragment(surah: Surah? = null): NavDirections =
        ActionQuranToAyahFragment(surah)
  }
}
