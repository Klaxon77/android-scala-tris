package com.klaxon.tris.classical

import android.widget.TextView
import com.klaxon.tris.game.GameListener
import com.klaxon.tris.common.Matrix

/**
 * ScoreController class is listening to game, calculates scores and shows score on the view
 * <p>date 12/27/13 </p>
 * @author klaxon
 */
class ScoreController(scoreView: TextView) extends GameListener {
  override def onScoreChanged(score: Int): Unit = scoreView.setText(score.toString)
}

