package com.klaxon.tris.classical

import android.app.Activity
import android.os.Bundle
import com.klaxon.tris.R
import com.klaxon.tris.classical.view.ClassicView
import com.klaxon.tris.game.{GameListener, Game}
import android.widget.Toast
import android.content.res.Resources
import android.content.Intent

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/2/13</p>
 */
class ClassicGameActivity extends Activity {

  var game: Game = _
  var pauseDialog: PauseDialog = _
  var isPaused = false

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.classical_layout)

    game = newGame()
    pauseDialog = newPauseDialog(game)
  }

  private def newGame(): Game = {
    val game = new ClassicGame(new ClassicGameInfo(getResources), ClassicGameActivity.FPS)
    game.addListener(gameListener())
    game.addListener(gameOverListener())

    initManipulator(game)
    game
  }

  private def gameListener(): GameListener = new ClassicView(findViewById(R.id.screen))

  private def gameOverListener(): GameListener = new GameListener {
    override def onGameOver(): Unit = finishWithResult(game.score)
  }

  private def initManipulator(game: Game) {
    val screen = findViewById(R.id.screen)
    screen.setOnTouchListener(new TouchPadListener(game, getResources.getDimensionPixelSize(R.dimen.block_size)))
  }

  private def newPauseDialog(game: Game): PauseDialog = new PauseDialog(this, new PauseDialogListener {
    def onExitPressed(): Unit = finishWithResult(game.score)
    def onResumePressed(): Unit = game.start(); isPaused = false
  })

  private def finishWithResult(score: Int) = {
    val intent = new Intent()
    intent.putExtra(ClassicGameActivity.SCORE_KEY, score)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }

  override def onPause(): Unit = {
    super.onPause()
    game.pause()
    isPaused = true
  }

  override def onResume(): Unit = {
    super.onResume()
    if (isPaused) pauseDialog.show()
    else game.start()
  }

  override def onBackPressed(): Unit = {
    isPaused = true
    game.pause()
    pauseDialog.show()
  }

}

object ClassicGameActivity {
  private val FPS = 60
  val SCORE_KEY = "score"
}

private class ClassicGameInfo(r: Resources) extends GameInfo {
  private val gWidth = r.getDimensionPixelSize(R.dimen.game_width)
  private val gHeight = r.getDimensionPixelSize(R.dimen.game_height)
  private val blockSize = r.getDimensionPixelSize(R.dimen.block_size)

  def gameWidth: Int = gWidth
  def gameHeight: Int = gHeight
  def blockWidth: Int = blockSize
  def blockHeight: Int = blockSize
}