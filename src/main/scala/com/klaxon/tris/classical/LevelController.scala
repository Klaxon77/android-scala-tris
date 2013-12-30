package com.klaxon.tris.classical

import com.klaxon.tris.game.GameListener
import android.widget.TextView

/**
 * <p>date 12/29/13 </p>
 * @author klaxon
 */
class LevelController(levelView: TextView) extends GameListener {
  override def onLevelChanged(level: Int): Unit = levelView.setText(level.toString)
}


