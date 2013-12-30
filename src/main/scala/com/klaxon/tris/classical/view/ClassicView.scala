package com.klaxon.tris.classical.view

import com.klaxon.tris.common.Matrix
import com.klaxon.tris.game.{GameListener, WorldState}
import com.klaxon.tris.R
import android.view.View
import android.widget.TextView

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class ClassicView(v: View) extends GameListener{
  val figureView = v.findViewById(R.id.figureView).asInstanceOf[FigureView]
  val gameArea = v.findViewById(R.id.game_area).asInstanceOf[GameArea]
  val levelTextView = v.findViewById(R.id.level).asInstanceOf[TextView]
  val scoreTextView = v.findViewById(R.id.score).asInstanceOf[TextView]

  override def onNextFigureChanged(figure: Matrix): Unit = figureView.setFigure(figure)

  override def onWorldChanged(world: WorldState): Unit = gameArea.setWorld(world)

  override def onScoreChanged(score: Int): Unit = scoreTextView.setText(score.toString)

  override def onLevelChanged(level: Int): Unit = levelTextView.setText(level.toString)
}
