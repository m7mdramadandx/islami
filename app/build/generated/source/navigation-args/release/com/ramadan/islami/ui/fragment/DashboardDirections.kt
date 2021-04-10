package com.ramadan.islami.ui.fragment

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.ramadan.islami.R
import kotlin.Int
import kotlin.String

public class DashboardDirections private constructor() {
  private data class ActionDashboardToFamilyTreeDetails(
    public val intentKey: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_dashboard_to_family_tree_details

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("intentKey", this.intentKey)
      return result
    }
  }

  public companion object {
    public fun actionNavDashboardToNavQuran(): NavDirections =
        ActionOnlyNavDirections(R.id.action_nav_dashboard_to_nav_quran)

    public fun actionDashboardToStories(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_stories)

    public fun actionDashboardToDaily(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_daily)

    public fun actionDashboardToQuotes(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_quotes)

    public fun actionDashboardToTopics(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_topics)

    public fun actionDashboardToFamilyTree(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_family_tree)

    public fun actionDashboardToFamilyTreeDetails(intentKey: String): NavDirections =
        ActionDashboardToFamilyTreeDetails(intentKey)

    public fun actionDashboardToFeedback(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_feedback)

    public fun actionDashboardToSettings(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_settings)

    public fun actionDashboardToShare(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_share)
  }
}
