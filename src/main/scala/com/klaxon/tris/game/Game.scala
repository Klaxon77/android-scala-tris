package com.klaxon.tris.game

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
trait Game extends GamePadListener {

  def start()
  def pause()
  def setSpeed(speed: Int)
  def setGameListener(g: GameListener)

  trait GameListener {
    def onGameOver()
    def onLinesDestroy(linesCount: Int)
  }

}
