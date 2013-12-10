package com.klaxon.tris.classical

import android.app.Activity
import android.os.Bundle
import com.klaxon.tris.R
import com.klaxon.tris.classical.view.ClassicView
import android.view.{MotionEvent, View}
import com.klaxon.tris.game.{GamePadListener, GameListener, Game}
import android.view.View.OnTouchListener
import android.widget.Toast
import android.util.Log

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/2/13</p>
 */
class ClassicalActivity extends Activity {

  val FPS = 60
  var game: Game = null

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.classical_layout)

    val view = new ClassicView(findViewById(R.id.screen))
    game = new ClassicGame(view, FPS)
    game.setSpeed(9)
    game.setGameListener(gameListener())

    initController()
    game.start()
  }

  private def initController() {
    val screen = findViewById(R.id.screen)
    screen.setOnTouchListener(new TouchPadListener(game))
  }

  private def gameListener(): GameListener = new GameListener {
    def onGameOver(): Unit = {
      Toast.makeText(ClassicalActivity.this, "Game over", 1500).show()
      finish()
    }

    def onLinesDestroy(linesCount: Int): Unit = {}
  }

}

import android.view.MotionEvent._

class TouchPadListener(gamePad: GamePadListener) extends OnTouchListener {

  var lastAction: Int = -1
  var lastX = 0.
  var lastY = 0.

  def onTouch(v: View, event: MotionEvent): Boolean = {
    val x = event.getX
    val y = event.getY

    event.getAction match {
      case ACTION_UP if lastAction == ACTION_DOWN => gamePad.rotate()
      case ACTION_MOVE => moveDirection(x, y) match {
        case LEFT_DIRECTION => gamePad.left(1)
        case RIGHT_DIRECTION => gamePad.right(1)
        case DOWN_DIRECTION => gamePad.down(1)
      }
      case _ =>
    }

    lastAction = event.getAction
    lastX = x
    lastY = y
    true
  }

  val LEFT_DIRECTION = -1
  val DOWN_DIRECTION = 0
  val RIGHT_DIRECTION = 1

  def moveDirection(x: Float, y: Float): Int = {
    val dx = x - lastX

    if (dx > 0) RIGHT_DIRECTION
    else LEFT_DIRECTION
  }

}
