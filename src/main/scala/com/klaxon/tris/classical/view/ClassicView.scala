package com.klaxon.tris.classical.view

import com.klaxon.tris.classical.GameView
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.game.WorldState
import com.klaxon.tris.R
import android.view.View
import android.util.Log

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class ClassicView(v: View) extends GameView{
  val figureView = v.findViewById(R.id.figureView).asInstanceOf[FigureView]
  val gameArea = v.findViewById(R.id.game_area).asInstanceOf[GameArea]
  val resources = v.getResources

  def gameWidth: Int = resources.getDimensionPixelSize(R.dimen.game_width)

  def gameHeight: Int = resources.getDimensionPixelSize(R.dimen.game_height)

  def blockWidth: Int = resources.getDimensionPixelSize(R.dimen.block_size)

  def blockHeight: Int = resources.getDimensionPixelSize(R.dimen.block_size)

  def update(world: WorldState): Unit = {
    Log.w("OLO", "update start")
    gameArea.setWorld(world)
    Log.w("OLO", "update end")
  }

  def updateNextFigure(m: Matrix): Unit = {
    Log.w("OLO", "next figure start")
    figureView.setFigure(m)
    Log.w("OLO", "next figure start")
  }
}
