package com.klaxon.tris.game

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
trait Game extends GamePadListener with GameObservable {

  def start()
  def pause()
  def setSpeed(speed: Int)

}
