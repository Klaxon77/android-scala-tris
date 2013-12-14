package com.klaxon.tris.classical

import android.graphics.{Paint, Bitmap, Canvas}
import com.klaxon.tris.common.Matrix
import android.graphics.drawable.{BitmapDrawable, Drawable}
import android.content.res.Resources
import com.klaxon.tris.R

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/6/13</p>
 */
object MatrixDrawer {

  val paint = new Paint

  def makeDrawable(m: Matrix, res: Resources): Drawable = {
    val blockSize = res.getDimension(R.dimen.block_size).toInt

    val bitmap = Bitmap.createBitmap(m.width() * blockSize, m.height() * blockSize, Bitmap.Config.ARGB_8888)
    val canvas = new Canvas(bitmap)

    for (i <- 0 until m.width()) {
      val left = blockSize * i

      for (j <- 0 until m.height() if m(j)(i) != 0) {
        val top = blockSize * j
        val bottom = top + blockSize
        val right = left + blockSize

        val block = res.getDrawable(BlockFactory.block(m(j)(i)))
        block.setBounds(left, top, right, bottom)
        block.draw(canvas)
      }
    }

    new BitmapDrawable(res, bitmap)
  }

}
