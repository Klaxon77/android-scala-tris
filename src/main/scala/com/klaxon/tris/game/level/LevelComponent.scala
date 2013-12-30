package com.klaxon.tris.game.level

import com.klaxon.tris.game.Game


/**
  * <p>date 12/29/13 </p>
  * @author klaxon
  */
trait LevelComponent {
   val levelCalculator: LevelCalculator

   trait LevelCalculator {
     def currentLevel: Int
     def onLinesDestroyed(game: Game, lines: Int, cellsInLine: Int)
   }

 }
