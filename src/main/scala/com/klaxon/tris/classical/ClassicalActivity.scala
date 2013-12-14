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
    game.setSpeed(2)
    game.setGameListener(gameListener())

    initTouchPad()
    game.start()
  }

  private def initTouchPad() {
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