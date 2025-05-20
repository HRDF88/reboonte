package com.openclassrooms.rebonnte.utils.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityManager

object AccessibilityTalkBackEnabled {

    fun isTalkBackEnabled(context: Context): Boolean {
        val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
        return accessibilityManager?.isTouchExplorationEnabled == true
    }
}