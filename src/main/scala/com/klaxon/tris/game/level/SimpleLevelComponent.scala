package com.klaxon.tris.game.level

import com.klaxon.tris.game.Game


/**
 * <p>date 12/29/13 </p>
 * @author klaxon
 */
trait SimpleLevelComponent extends LevelComponent {
  val levelCalculator: LevelCalculator = new SimpleLevelCalculator

  private class SimpleLevelCalculator extends LevelCalculator {
    private var level: Int = SimpleLevelCalculator.MIN_LEVEL
    private var linesDestroyedInThisLevel = 0

    def currentLevel: Int = level

    override def onLinesDestroyed(g: Game, lines: Int, cellsInLine: Int) = if (level < SimpleLevelCalculator.MAX_LEVEL) {
      linesDestroyedInThisLevel += lines

      if (linesDestroyedInThisLevel > SimpleLevelCalculator.LINES_TO_LEVEL_UP) {
        level += 1
        linesDestroyedInThisLevel = 0
      }
    }
  }

  private object SimpleLevelCalculator {
    private val MIN_LEVEL = 1
    private val MAX_LEVEL = 15
    private val LINES_TO_LEVEL_UP = 10
  }

}
