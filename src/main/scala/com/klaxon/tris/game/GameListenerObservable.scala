package com.klaxon.tris.game

import com.klaxon.tris.common.Matrix


/**
 * <p>date: 12/26/13 </p>
 * @author klaxon
 */
trait GameListener {
  def onGameOver() = {}
  def onScoreChanged(score: Int) = {}
  def onLevelChanged(level: Int) = {}
  def onNextFigureChanged(figure: Matrix) = {}
  def onWorldChanged(world: WorldState) = {}
}

trait GameObservable {

  private var listeners: List[GameListener] = Nil
  def addListener(listener: GameListener) = listeners ::= listener

  protected def notifyGameOver() = listeners.foreach(_.onGameOver())
  protected def notifyScoreChanged(score: Int) = listeners.foreach(_.onScoreChanged(score))
  protected def notifyLevelChanged(level: Int) = listeners.foreach(_.onLevelChanged(level))
  protected def notifyNextFigureChanged(figure: Matrix) = listeners.foreach(_.onNextFigureChanged(figure))
  protected def notifyWorldChanged(world: WorldState) = listeners.foreach(_.onWorldChanged(world))
  
}

