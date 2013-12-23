package com.klaxon.tris.classical.view

import android.graphics.drawable.Drawable
import android.graphics.{PixelFormat, Canvas, ColorFilter}
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.classical.BlockFactory
import android.content.res.Resources

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/17/13</p>
 */
class MatrixDrawable(m: Matrix, blockSize: Int, res: Resources) extends Drawable {

  def draw(canvas: Canvas): Unit = {
    for (i <- 0 until m.width()) {
      val left = blockSize * i + getBounds.left

      for (j <- 0 until m.height() if m(j)(i) != 0) {
        val top = blockSize * j + getBounds.top
        val bottom = top + blockSize
        val right = left + blockSize

        val block = BlockFactory.blockDrawable(m(j)(i), res)
        block.setBounds(left, top, right, bottom)
        block.draw(canvas)
      }
    }
  }

  override def getIntrinsicWidth: Int = m.width() * blockSize

  override def getIntrinsicHeight: Int = m.height() * blockSize

  def getOpacity: Int = PixelFormat.OPAQUE

  //Until this methods are unused I won't implement them. YAGNI rules!
  def setAlpha(alpha: Int): Unit = ???

  def setColorFilter(cf: ColorFilter): Unit = ???


}
