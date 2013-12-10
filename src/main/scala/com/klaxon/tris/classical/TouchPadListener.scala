package com.klaxon.tris.classical

import android.view.{View, MotionEvent, GestureDetector}
import com.klaxon.tris.game.GamePadListener
import android.view.View.OnTouchListener
import android.view.GestureDetector.SimpleOnGestureListener


class TouchPadListener(gamePad: GamePadListener) extends OnTouchListener {
  val gestureDetector = new GestureDetector(new SwipeListener(gamePad))

  def onTouch(v: View, event: MotionEvent): Boolean = {
    gestureDetector.onTouchEvent(event)
  }
}

final case class SwipeListener(gamePad: GamePadListener) extends SimpleOnGestureListener {
  //TODO: constants to companion object
  val SWIPE_THRESHOLD = 14
  val SWIPE_VELOCITY_THRESHOLD = 14

  override def onSingleTapConfirmed(e: MotionEvent): Boolean = {
    gamePad.rotate()
    true
  }


  override def onDoubleTap(e: MotionEvent): Boolean = {
    gamePad.rotate()
    true
  }


  override def onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean = {
    val diffX = e2.getX - e1.getX
    val diffY = e2.getY - e1.getY

    if (math.abs(diffX) > math.abs(diffY)) {
      if (math.abs(diffX) > SWIPE_THRESHOLD) {
        if (diffX > 0) {
          gamePad.right(0)
        } else {
          gamePad.left(0)
        }
      }
    } else {
      if (math.abs(diffY) > SWIPE_THRESHOLD && diffY > 0) {
        gamePad.down(0)
      }
    }


    true
  }

  override def onDown(e: MotionEvent): Boolean = true
}
