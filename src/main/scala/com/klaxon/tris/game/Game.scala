package com.klaxon.tris.game

import com.klaxon.tris.game.score.ScoreCalculatorComponent
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.game.level.LevelComponent

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
trait Game extends GamePadListener with GameObservable {
  self: ScoreCalculatorComponent with LevelComponent =>

  protected def onLinesDestroyed(linesCount: Int, cellsInLine: Int) = {
    scoreCalculator.onLinesDestroyed(this, linesCount, cellsInLine)
    notifyScoreChanged(scoreCalculator.currentScore)

    levelCalculator.onLinesDestroyed(this, linesCount, cellsInLine)
    notifyLevelChanged(levelCalculator.currentLevel)
  }

  protected def onFigureAdded(figure: Matrix) = {
    scoreCalculator.onFigureAdded(this, figure)
    notifyScoreChanged(scoreCalculator.currentScore)
  }

  def start()
  def pause()
  def level: Int = levelCalculator.currentLevel
  def score: Int = scoreCalculator.currentScore

}
