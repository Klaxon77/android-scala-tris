package com.klaxon.tris.classical.view

import android.content.Context
import android.util.AttributeSet
import android.view.{SurfaceView, View}
import com.klaxon.tris.game.WorldState
import android.graphics.{Rect, Canvas}
import com.klaxon.tris.R
import android.graphics.drawable.Drawable

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/8/13</p>
 */
class GameArea(c: Context, a: AttributeSet) extends SurfaceView(c, a) {

  var world: WorldState = null
  val blockSize = getResources.getDimensionPixelSize(R.dimen.block_size)

  def setWorld(world: WorldState) = {
    this.world = world
    invalidate()
  }

  override def onDraw(canvas: Canvas): Unit = {
    super.onDraw(canvas)

    if (world == null) {
      return
    }

    val boardDrawable = new MatrixDrawable(world.board, blockSize, getResources)
    boardDrawable.setBounds(0, 0, getWidth, getHeight)
    boardDrawable.draw(canvas)

    val figureDrawable = new MatrixDrawable(world.figure, blockSize, getResources)
    figureDrawable.setBounds(bounds(world.position.x, world.position.y, figureDrawable))
    figureDrawable.draw(canvas)
  }

  private def bounds(x: Int, y: Int, d: Drawable) = new Rect(x, y, x + d.getMinimumWidth, y + d.getMinimumHeight)

}
