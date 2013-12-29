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

  private val scoreCalculator = new ScoreCalculator
  scoreView.setText(scoreCalculator.currentScore.toString)

  override def onLinesDestroy(linesCount: Int, cellsInLine: Int): Unit = {
    scoreCalculator.onLinesDestroy(linesCount, cellsInLine)
    scoreView.setText(scoreCalculator.currentScore.toString)
  }

  override def onFigureAdded(figure: Matrix): Unit = {
    scoreCalculator.onFigureAdded(figure)
    scoreView.setText(scoreCalculator.currentScore.toString)
  }

  def score = scoreCalculator.currentScore

}

private class ScoreCalculator extends GameListener {

  var currentScore: Int = 0

  override def onLinesDestroy(linesCount: Int, cellsInLine: Int) = currentScore += math.pow(linesCount, 1.5).toInt * cellsInLine

  override def onFigureAdded(figure: Matrix) = {
    val blocksInFigure = figure.array.view.flatten.count(_ != 0)
    currentScore += blocksInFigure / 2
  }
}

