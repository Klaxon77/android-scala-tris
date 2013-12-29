package com.klaxon.tris.classical.view

import com.klaxon.tris.R
import android.graphics.drawable.Drawable
import android.content.res.Resources

/**
 * Date: 12/6/13
 */
object BlockFactory {

  var blockMap = Map[Int, Drawable]()

  def blockDrawable(i: Int, res: Resources) = blockMap.get(i) match {
    case Some(drawable) => drawable
    case None => {
      val drawable = res.getDrawable(drawableId(i))
      blockMap = blockMap updated(i, drawable)
      drawable
    }
  }

  private def drawableId(i: Int) = i match {
    case 1 => R.drawable.red_block
    case 2 => R.drawable.green_block
    case 3 => R.drawable.blue_block
    case 4 => R.drawable.orange_block
    case 5 => R.drawable.yellow_block
    case 6 => R.drawable.white_block
    case 7 => R.drawable.pink_block
    case _ => throw new UnsupportedOperationException()
  }

}
