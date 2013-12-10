package com.klaxon.tris.classical.view

import android.view.View
import android.graphics._
import android.content.Context
import android.util.AttributeSet
import android.graphics.drawable.Drawable
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.classical.MatrixDrawer

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/4/13</p>
 */
class FigureView(c: Context, a: AttributeSet) extends View(c, a) {

  var matrix: Matrix = Matrix.emptyMatrix()

  def setFigure(m: Matrix) = {
    matrix = m
    invalidate()
  }

  override def onDraw(canvas: Canvas): Unit = {
    super.onDraw(canvas)

    if (matrix == null) return

    val figureDrawable = MatrixDrawer.makeDrawable(matrix, getResources)
    figureDrawable.setBounds(centerBoundsFor(figureDrawable))
    figureDrawable.draw(canvas)
  }

  private def centerBoundsFor(drawable: Drawable):Rect = {
    val drawableWidth = drawable.getIntrinsicWidth
    val drawableHeight = drawable.getIntrinsicHeight

    val left = (getWidth - drawableWidth) / 2
    val top = (getHeight - drawableHeight) / 2
    val right = left + drawableWidth
    val bottom = top + drawableHeight

    new Rect(left, top, right, bottom)
  }

}
