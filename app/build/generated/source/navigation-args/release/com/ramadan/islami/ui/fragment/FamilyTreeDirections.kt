package com.ramadan.islami.ui.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.ramadan.islami.R
import kotlin.Int
import kotlin.String

public class FamilyTreeDirections private constructor() {
  private data class ActionFamilyTreeToFamilyTreeDetails(
    public val intentKey: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_family_tree_to_family_tree_details

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("intentKey", this.intentKey)
      return result
    }
  }

  public companion object {
    public fun actionFamilyTreeToFamilyTreeDetails(intentKey: String): NavDirections =
        ActionFamilyTreeToFamilyTreeDetails(intentKey)
  }
}
