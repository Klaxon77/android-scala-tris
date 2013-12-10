package com.klaxon.tris.classical

import com.klaxon.tris.game.{GameListener, WorldState, GameLoop, Game}
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.figures.MatrixFactory
import android.graphics.Point

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class ClassicGame(view: GameView, fps: Int) extends Game {

  val BOARD_WIDTH = 10
  val BOARD_HEIGHT = 20
  val board = new Matrix(BOARD_WIDTH, BOARD_HEIGHT)

  var gameListener: GameListener = null
  var gameLoop: GameLoop = null
  var currentFigure = MatrixFactory.randFigure()
  var nextFigure = MatrixFactory.randFigure()
  var position = initialPositionFor(currentFigure)
  var speed = 1

  view.updateNextFigure(nextFigure)

  def left(velocity: Int): Unit = {
    var x = (position.x / view.blockWidth) - 1
    x = if (x >= 0) x else 0
    val y = position.y / view.blockHeight

    if (!collision(x, y)) {
      position = new Point(x * view.blockWidth, y * view.blockHeight)
      view.update(new WorldState(board, currentFigure, position))
    }
  }

  def right(velocity: Int): Unit = {
    var x = (position.x / view.blockWidth) + 1
    x = if (x < BOARD_WIDTH - currentFigure.width()) x else BOARD_WIDTH - currentFigure.width()
    val y = position.y / view.blockHeight

    if (!collision(x, y)) {
      position = new Point(x * view.blockWidth, y * view.blockHeight)
      view.update(new WorldState(board, currentFigure, position))
    }
  }

  def down(velocity: Int): Unit = {

  }

  def rotate(): Unit = {
    val rotatedFigure = currentFigure.rotateRight()
    val x = position.x / view.blockWidth
    val y = position.y / view.blockHeight - (currentFigure.width() - currentFigure.height())

    if (!collision(x, y)) {
      currentFigure = rotatedFigure

      position = new Point(position.x, position.y + (currentFigure.width() - currentFigure.height()) * view.blockHeight)
      view.update(new WorldState(board, currentFigure, position))
    }
  }

  def start(): Unit = {
    gameLoop = new GameLoop(fps)
    gameLoop.loop {
      update()
    }
  }

  def update(): Unit = {
    val newPosition = nextPosition()

    val x = newPosition.x / view.blockWidth
    val y = newPosition.y / view.blockHeight + 1
    if (collision(x, y)) {
      addFigureToBoard(currentFigure, x, y - 1)
      currentFigure = nextFigure
      nextFigure = MatrixFactory.randFigure()
      view.updateNextFigure(nextFigure)
      position = initialPositionFor(currentFigure)
      if (collision(nextPosition().x / view.blockWidth, nextPosition().y / view.blockHeight + 1)) {
        gameLoop.stop()
        gameListener.onGameOver()
      }

      view.update(new WorldState(board, currentFigure, position))
      return
    }

    if (y >= BOARD_HEIGHT - currentFigure.height()) {
      addFigureToBoard(currentFigure, x, BOARD_HEIGHT - currentFigure.height())
      currentFigure = nextFigure
      nextFigure = MatrixFactory.randFigure()
      view.updateNextFigure(nextFigure)
      position = initialPositionFor(currentFigure)
      view.update(new WorldState(board, currentFigure, position))
      return
    }

    position = newPosition
    view.update(new WorldState(board, currentFigure, position))
  }

  def collision(x: Int, y: Int): Boolean = {
    for (i <- 0 until currentFigure.width()) {
      for (j <- 0 until currentFigure.height() if currentFigure(i)(j) != 0) {
        val xPos = i + x
        val yPos = j + y

        if (xPos > BOARD_WIDTH || xPos < 0) return false
        if (yPos > BOARD_HEIGHT || yPos < 0) return false

        if (board(xPos)(yPos) != 0) return true
      }
    }

    false
  }

  def addFigureToBoard(figure: Matrix, x: Int, y: Int) = {
    for (i <- 0 until figure.width()) {
      for (j <- 0 until figure.height() if figure(i)(j) != 0) {
        board(x + i)(y + j) = figure(i)(j)
      }
    }
  }

  def nextPosition() = new Point(position.x, position.y + speed)

  def pause(): Unit = {
    gameLoop.stop()
  }

  def setSpeed(speed: Int): Unit = this.speed = speed

  def setGameListener(g: GameListener): Unit = gameListener = g

  private def initialPositionFor(m: Matrix): Point = {
    val x = (BOARD_WIDTH - m.width()) / 2 * view.blockWidth
    val y = -(m.height() * view.blockHeight)
    new Point(x, y)
  }

}