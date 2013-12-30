package com.klaxon.tris.classical

import android.view.{View, MotionEvent, GestureDetector}
import com.klaxon.tris.game.GamePadListener
import android.view.View.OnTouchListener
import android.view.GestureDetector.SimpleOnGestureListener


class TouchPadListener(gamePad: GamePadListener, swipeThreshold: Int) extends OnTouchListener {
  val gestureDetector = new GestureDetector(new SwipeListener(gamePad, swipeThreshold))

  def onTouch(v: View, event: MotionEvent): Boolean = {
    gestureDetector.onTouchEvent(event)
  }
}

final case class SwipeListener(gamePad: GamePadListener, swipeThreshold: Int) extends SimpleOnGestureListener {
  var xAccumulator = 0
  var hasMoved = false

  override def onSingleTapUp(e: MotionEvent): Boolean = {
    if (!hasMoved) gamePad.rotate()
    true
  }

  override def onDoubleTap(e: MotionEvent): Boolean = onSingleTapUp(e)

  override def onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean = {
    xAccumulator += distanceX.toInt
    if (xAccumulator.abs > swipeThreshold) {
      val move: () => Unit = if (xAccumulator < 0) gamePad.right else gamePad.left

      for (i <- 0 until (xAccumulator.abs / swipeThreshold)) {
        move()
      }

      xAccumulator = 0
      hasMoved = true
    }

    if (hasMoved) return true

    if (distanceY < -swipeThreshold) {
      gamePad.down()
      hasMoved = true
    }

    true
  }

  override def onDown(e: MotionEvent): Boolean = {
    xAccumulator = 0
    hasMoved = false
    true
  }
}
