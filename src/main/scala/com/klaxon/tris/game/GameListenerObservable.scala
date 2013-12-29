package com.klaxon.tris.game

import com.klaxon.tris.common.Matrix

/**
 * <p>date: 12/26/13 </p>
 * @author klaxon
 */
trait GameListener {
  def onGameOver() = {}
  def onLinesDestroy(linesCount: Int, cellsInLine: Int) = {}
  def onFigureAdded(figure: Matrix) = {}
}

trait GameObservable {

  private var listeners: List[GameListener] = Nil
  def addListener(listener: GameListener) = listeners ::= listener

  protected def notifyGameOver() = listeners.foreach(_.onGameOver())
  protected def notifyOnLinesDestroy(linesCount: Int, cellsInLine: Int) = listeners.foreach(_.onLinesDestroy(linesCount, cellsInLine))
  protected def notifyFigureAdded(figure: Matrix) = listeners.foreach(_.onFigureAdded(figure))
}

