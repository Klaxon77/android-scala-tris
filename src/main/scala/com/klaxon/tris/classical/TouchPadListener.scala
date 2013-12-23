package com.klaxon.tris.classical

import android.view.{View, MotionEvent, GestureDetector}
import com.klaxon.tris.game.GamePadListener
import android.view.View.OnTouchListener
import android.view.GestureDetector.SimpleOnGestureListener
import android.util.Log


class TouchPadListener(gamePad: GamePadListener) extends OnTouchListener {
  val gestureDetector = new GestureDetector(new SwipeListener(gamePad))

  def onTouch(v: View, event: MotionEvent): Boolean = {
    gestureDetector.onTouchEvent(event)
  }
}

final case class SwipeListener(gamePad: GamePadListener) extends SimpleOnGestureListener {
  //TODO: constants to companion object
  val SWIPE_THRESHOLD = 40

  var xAccumulator = 0
  var hasMoved = false

  override def onSingleTapUp(e: MotionEvent): Boolean = {
    if (!hasMoved) gamePad.rotate()
    true
  }

  override def onDoubleTap(e: MotionEvent): Boolean = onSingleTapUp(e)

  override def onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean = {
    val diffX = e2.getX - e1.getX
    xAccumulator += diffX.toInt
    if (xAccumulator.abs > SWIPE_THRESHOLD) {
      val move:()=>Unit = if (xAccumulator > 0) gamePad.right else gamePad.left
      for (i <- 0 until (xAccumulator.abs / SWIPE_THRESHOLD)) {
        move()
      }

      xAccumulator = 0
      hasMoved = true
    }

    if (hasMoved) return true

    val diffY = e2.getY - e1.getY
    if (diffY.abs > SWIPE_THRESHOLD && diffY > 0) {
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
