package com.openclassrooms.rebonnte.utils.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityManager

/**
 * Utility object to check if TalkBack (Android's screen reader) is enabled.
 */
object AccessibilityTalkBackEnabled {

    /**
     * Checks whether TalkBack (touch exploration) is enabled on the device.
     *
     * @param context The [Context] to access system services.
     * @return `true` if TalkBack (touch exploration) is enabled, `false` otherwise.
     */
    fun isTalkBackEnabled(context: Context): Boolean {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
        return accessibilityManager?.isTouchExplorationEnabled == true
    }
}