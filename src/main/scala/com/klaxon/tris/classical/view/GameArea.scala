package com.klaxon.tris.classical.view

import android.content.Context
import android.util.{Log, AttributeSet}
import android.view.View
import com.klaxon.tris.game.WorldState
import android.graphics.Canvas
import com.klaxon.tris.classical.MatrixDrawer
import android.widget.Toast

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/8/13</p>
 */
class GameArea(c: Context, a: AttributeSet) extends View(c, a) {

  var world: WorldState = null

  def setWorld(world: WorldState) = {
    this.world = world
    invalidate()
  }

  override def onDraw(canvas: Canvas): Unit = {
    super.onDraw(canvas)

    if (world == null) {
      Toast.makeText(getContext, "null", 300).show()
      return
    }

    val boardDrawable = MatrixDrawer.makeDrawable(world.board, getResources)
    boardDrawable.setBounds(0, 0, getWidth, getHeight)
    boardDrawable.draw(canvas)

    val figureDrawable = MatrixDrawer.makeDrawable(world.figure, getResources)
    figureDrawable.setBounds(world.position.x, world.position.y, world.position.x + figureDrawable.getIntrinsicWidth, world.position.y + figureDrawable.getIntrinsicHeight)
    figureDrawable.draw(canvas)
  }
}
