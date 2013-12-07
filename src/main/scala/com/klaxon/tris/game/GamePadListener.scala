package com.klaxon.tris.game

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
trait GamePadListener {

  def left(velocity: Int)
  def right(velocity: Int)
  def down(velocity: Int)
  def rotate()

}
