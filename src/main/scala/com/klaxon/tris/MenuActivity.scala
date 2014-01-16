package com.klaxon.tris

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.content.Intent
import com.klaxon.tris.classical.ClassicGameActivity
import com.klaxon.tris.topscore.TopScoreActivity
import com.klaxon.tris.storage.ScoreDao
import com.klaxon.tris.MenuActivity.function2onClickListener

class MenuActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.menu)

    findViewById(R.id.play).setOnClickListener((v: View) => startClassicGame())
    findViewById(R.id.score_board).setOnClickListener((v: View) => startTopScore())
    findViewById(R.id.exit).setOnClickListener((v: View) => finish())
  }

  private def startClassicGame() = {
    val intent = new Intent(this, classOf[ClassicGameActivity])
    startActivityForResult(intent, MenuActivity.CLASSIC_GAME_REQUEST_CODE)
  }

  private def startTopScore() = {
    val intent = new Intent(this, classOf[TopScoreActivity])
    startActivity(intent)
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) = if (resultCode == Activity.RESULT_OK) {
    requestCode match {
      case MenuActivity.CLASSIC_GAME_REQUEST_CODE => {
        val score = data.getIntExtra(ClassicGameActivity.SCORE_KEY, 0)
        saveIfRecord(score)
      }
    }
  }

  private def saveIfRecord(score: Int) = {
    val scoreDao = new ScoreDao(this)
    scoreDao.saveScore(score)

    val scores = scoreDao.scores
    if (scores.length > 3) scoreDao.removeScore(scores.min)
  }

}

object MenuActivity {
  private val CLASSIC_GAME_REQUEST_CODE = 0

  implicit def function2onClickListener(f: View => Unit): OnClickListener = new OnClickListener {
    def onClick(v: View) = f(v)
  }
}
