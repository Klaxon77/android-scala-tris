package com.klaxon.tris.game.score

import com.klaxon.tris.common.Matrix
import com.klaxon.tris.game.Game

/**
 * <p>date 12/29/13 </p>
 * @author klaxon
 */
trait ScoreCalculatorComponent {
  val scoreCalculator: ScoreCalculator

  trait ScoreCalculator {
    def currentScore: Int
    def onLinesDestroyed(game: Game, lines: Int, cellsInLine: Int)
    def onFigureAdded(game: Game, figure: Matrix)
  }

}
