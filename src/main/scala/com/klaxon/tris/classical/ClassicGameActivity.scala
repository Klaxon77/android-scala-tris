package com.klaxon.tris.classical

import android.app.Activity
import android.os.Bundle
import com.klaxon.tris.R
import com.klaxon.tris.classical.view.ClassicView
import android.view.{MotionEvent, View}
import com.klaxon.tris.game.{GamePadListener, GameListener, Game}
import android.view.View.OnTouchListener
import android.widget.{TextView, Toast}
import android.util.Log
import com.klaxon.tris.common.Matrix

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/2/13</p>
 */
class ClassicGameActivity extends Activity {

  val FPS = 60
  var game: Game = _

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.classical_layout)

    val view = new ClassicView(findViewById(R.id.screen))
    game = new ClassicGame(view, FPS)
    game.addListener(gameOverListener())
    game.addListener(scoreListener())

    initManipulator()
  }

  private def initManipulator() {
    val screen = findViewById(R.id.screen)
    screen.setOnTouchListener(new TouchPadListener(game))
  }

  private def gameOverListener(): GameListener = new GameListener {
    override def onGameOver(): Unit = {
      Toast.makeText(ClassicGameActivity.this, "Game over", 1500).show()
      finish()
    }
  }

  private def scoreListener(): GameListener = new ScoreController(findViewById(R.id.score).asInstanceOf[TextView])

  override def onPause(): Unit = {
    super.onPause()
    game.pause()
  }

  override def onResume(): Unit = {
    super.onResume()
    game.start()
  }

}