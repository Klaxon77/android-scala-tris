package com.klaxon.tris.game

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/9/13</p>
 */
trait GameListener {
  def onGameOver()
  def onLinesDestroy(linesCount: Int)
}
