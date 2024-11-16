package dev.sargunv.maplibrekmp.core

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCAction
import platform.UIKit.UIGestureRecognizer
import platform.darwin.NSObject
import platform.darwin.sel_registerName

@OptIn(BetaInteropApi::class)
internal class Gesture<T : UIGestureRecognizer>(
  val recognizer: T,
  val isCooperative: Boolean = true,
  private val action: T.() -> Unit,
) : NSObject() {
  init {
    recognizer.addTarget(target = this, action = sel_registerName(::handleGesture.name + ":"))
  }

  @ObjCAction
  fun handleGesture(sender: UIGestureRecognizer) {
    @Suppress("UNCHECKED_CAST") action(sender as T)
  }
}
