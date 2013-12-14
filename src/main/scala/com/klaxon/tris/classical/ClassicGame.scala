package com.klaxon.tris.classical

import com.klaxon.tris.game.{GameListener, WorldState, GameLoop, Game}
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.figures.MatrixFactory
import android.graphics.Point
import scala.annotation.tailrec

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class ClassicGame(view: GameView, fps: Int) extends Game {

  val BOARD_WIDTH = 10
  val BOARD_HEIGHT = 20
  var board = new Matrix(BOARD_HEIGHT, BOARD_WIDTH)

  var gameListener: GameListener = null
  var gameLoop: GameLoop = null
  var currentFigure = MatrixFactory.randFigure()
  var nextFigure = MatrixFactory.randFigure()
  var position = initialPositionFor(currentFigure)
  var viewYPos = position.y * view.blockHeight
  var velocity = 1

  var horizontalMove = 0
  var downMove = false
  var rotateMove = false

  view.updateNextFigure(nextFigure)

  def left(): Unit = horizontalMove -= 1

  def right(): Unit = horizontalMove += 1

  def down(): Unit = downMove = true

  def rotate(): Unit = rotateMove = true

  def start(): Unit = {
    gameLoop = new GameLoop(fps)
    gameLoop.loop {
      update()
    }
  }

  def pause(): Unit = gameLoop.stop()

  def setSpeed(speed: Int): Unit = this.velocity = speed

  def setGameListener(g: GameListener): Unit = gameListener = g

  private def update(): Unit = {
    doHorizontalMove()
    doRotateMove()
    doDownMove()

    updateGame()
    updateView()
  }

  @tailrec
  private def doHorizontalMove(): Unit = horizontalMove match {
    case 0 => return
    case _ =>
      val movedPosition = new Point(position.x + horizontalMove.signum, position.y)

      if (collision(movedPosition, currentFigure)) {
        horizontalMove = 0
      } else {
        position = movedPosition
        horizontalMove -= horizontalMove.signum
      }

      doHorizontalMove()
  }

  private def doRotateMove() = if (rotateMove) {
    rotateMove = false

    val rotatedFigure = currentFigure.rotateRight()
    val newPosition = new Point(position.x, position.y)

    val cellsOutside = position.x + rotatedFigure.width() - BOARD_WIDTH
    if (cellsOutside > 0) newPosition.offset(-cellsOutside, 0)

    if (!collision(newPosition, rotatedFigure)) {
      currentFigure = rotatedFigure
      position = newPosition
    }
  }


  private def doDownMove(): Unit = if (downMove) {
    @tailrec
    def downMoveRec(): Unit = {
      val newPosition = nextPosition()
      if (!collision(newPosition, currentFigure)) {
        position = newPosition
        viewYPos = position.y * view.blockHeight
        downMoveRec()
      }
    }

    downMoveRec()
    downMove = false
  }

  def updateGame(): Unit = {
    viewYPos += velocity
    if (viewYPos / view.blockHeight < position.y) return

    val newPosition = nextPosition()
    if (!collision(newPosition, currentFigure)) {
      position = newPosition
      return
    }

    addCurrentFigureToBoard()
    destroyLines()
    println("BOARD", board)

    currentFigure = nextFigure
    nextFigure = MatrixFactory.randFigure()
    view.updateNextFigure(nextFigure)

    position = initialPositionFor(currentFigure)
    viewYPos = 0
    if (collision(position, currentFigure)) {
      stopGame()
    }

  }

  def destroyLines():Unit = {
    val newBoard = Array.ofDim[Int](BOARD_HEIGHT, BOARD_WIDTH)

    var y = BOARD_HEIGHT - 1
    for (i <- (BOARD_HEIGHT - 1) until 0 by -1) {
      if (!board(i).forall(_ != 0)) {
        newBoard(y) = board(i)
        y -= 1
      }
    }

    board = Matrix(newBoard)
  }

  private def collision(pos: Point, figure: Matrix): Boolean = {
    for (i <- 0 until figure.height()) {
      for (j <- 0 until figure.width() if figure(i)(j) != 0) {
        val yPos = i + pos.y
        val xPos = j + pos.x

        if (yPos >= BOARD_HEIGHT || yPos < 0) return true
        if (xPos >= BOARD_WIDTH || xPos < 0) return true

        if (board(yPos)(xPos) != 0) return true
      }
    }

    false
  }

  private def addCurrentFigureToBoard() = {
    for (i <- 0 until currentFigure.height()) {
      for (j <- 0 until currentFigure.width() if currentFigure(i)(j) != 0) {
        board(position.y + i)(position.x + j) = currentFigure(i)(j)
      }
    }
  }

  private def nextPosition() = new Point(position.x, position.y + 1)

  private def initialPositionFor(m: Matrix): Point = {
    val x = (BOARD_WIDTH - m.width()) / 2
    val y = 0 //1 - m.height()
    new Point(x, y)
  }

  private def stopGame() = {
    updateView()
    gameLoop.stop()
    gameListener.onGameOver()
  }

  private def updateView() = {
    val viewCurrentPosition = new Point(position.x * view.blockWidth, viewYPos)
    view.update(new WorldState(board, currentFigure, viewCurrentPosition))
  }

}