package com.klaxon.tris.classical

import com.klaxon.tris.R

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/6/13</p>
 */
object BlockFactory {

  def block(i: Int): Int = i match {
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
