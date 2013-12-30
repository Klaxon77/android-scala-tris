package com.klaxon.tris.classical

import android.app.Activity
import android.os.Bundle
import com.klaxon.tris.R
import com.klaxon.tris.classical.view.ClassicView
import com.klaxon.tris.game.{GameListener, Game}
import android.widget.{TextView, Toast}
import android.content.res.Resources
import android.util.Log

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

    game = new ClassicGame(new ClassicGameInfo(getResources), FPS)
    game.addListener(gameListener())
    game.addListener(gameOverListener())
    game.addListener(scoreListener())
    game.addListener(levelListener())

    initManipulator()
  }

  private def scoreListener(): GameListener = new ScoreController(findViewById(R.id.score).asInstanceOf[TextView])
  private def levelListener(): GameListener = new LevelController(findViewById(R.id.level).asInstanceOf[TextView])
  private def gameListener(): GameListener = new ClassicView(findViewById(R.id.screen))

  private def gameOverListener(): GameListener = new GameListener {
    override def onGameOver(): Unit = {
      Toast.makeText(ClassicGameActivity.this, "Game over", 1500).show()
      finish()
    }
  }

  private def initManipulator() {
    val screen = findViewById(R.id.screen)
    screen.setOnTouchListener(new TouchPadListener(game))
  }

  override def onPause(): Unit = {
    super.onPause()
    game.pause()
  }

  override def onResume(): Unit = {
    super.onResume()
    game.start()
  }

}

private class ClassicGameInfo(r: Resources) extends GameInfo {
  val gWidth = r.getDimensionPixelSize(R.dimen.game_width)
  val gHeight = r.getDimensionPixelSize(R.dimen.game_height)
  val blockSize = r.getDimensionPixelSize(R.dimen.block_size)

  def gameWidth: Int = gWidth
  def gameHeight: Int = gHeight
  def blockWidth: Int = blockSize
  def blockHeight: Int = blockSize
}