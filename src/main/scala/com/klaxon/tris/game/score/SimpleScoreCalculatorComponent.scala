package com.klaxon.tris.game.score

import com.klaxon.tris.common.Matrix
import com.klaxon.tris.game.Game

/**
 * <p>date 12/29/13 </p>
 * @author klaxon
 */
trait SimpleScoreCalculatorComponent extends ScoreCalculatorComponent {
  val scoreCalculator: ScoreCalculator = new SimpleScoreCalculator

  private class SimpleScoreCalculator extends ScoreCalculator {
    private var score: Int = 0

    def currentScore: Int = score

    override def onLinesDestroyed(game: Game, lines: Int, cellsInLine: Int) = score += (game.level * lines * cellsInLine * 0.2).toInt

    override def onFigureAdded(game: Game, figure: Matrix) = score += (game.level * 0.7).toInt
  }

}
