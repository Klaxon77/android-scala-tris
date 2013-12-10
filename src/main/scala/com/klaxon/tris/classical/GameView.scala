package com.klaxon.tris.classical

import com.klaxon.tris.game.WorldState
import com.klaxon.tris.common.Matrix

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
trait GameView {
  def gameWidth: Int
  def gameHeight: Int

  def blockWidth: Int
  def blockHeight: Int

  def update(world: WorldState)
  def updateNextFigure(m: Matrix)
}
