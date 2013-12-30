package com.klaxon.tris.classical

import com.klaxon.tris.game.{GameListener, WorldState, GameLoop, Game}
import com.klaxon.tris.common.Matrix
import com.klaxon.tris.figures.MatrixFactory
import android.graphics.Point
import scala.annotation.tailrec
import android.util.Log
import com.klaxon.tris.game.level.SimpleLevelComponent
import com.klaxon.tris.game.score.SimpleScoreCalculatorComponent

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class ClassicGame(gameInfo: GameInfo, fps: Int) extends Game with SimpleLevelComponent with SimpleScoreCalculatorComponent {

  private val BOARD_WIDTH = 10
  private val VISIBLE_BOARD_PART_HEIGHT = 20
  private val INVISIBLE_BOARD_PART_HEIGHT = ClassicGame.calculateMaxFigureHeight() - 1
  private val TOTAL_BOARD_HEIGHT = VISIBLE_BOARD_PART_HEIGHT + INVISIBLE_BOARD_PART_HEIGHT

  private val gameLoop = new GameLoop(fps)

  private var board = new Matrix(TOTAL_BOARD_HEIGHT, BOARD_WIDTH)
  private var currentFigure = MatrixFactory.randFigure()
  private var nextFigure = MatrixFactory.randFigure()
  private var position = initialPositionFor(currentFigure)
  private var viewYPos = position.y * gameInfo.blockHeight

  private var horizontalMove = 0
  private var downMove = false
  private var rotateMove = false

  def left(): Unit = horizontalMove -= 1

  def right(): Unit = horizontalMove += 1

  def down(): Unit = downMove = true

  def rotate(): Unit = rotateMove = true

  override def start(): Unit = {
    notifyScoreChanged(scoreCalculator.currentScore)
    notifyLevelChanged(levelCalculator.currentLevel)
    notifyNextFigureChanged(nextFigure)

    gameLoop.stop()
    gameLoop.loop {
      update()
    }
  }

  def pause(): Unit = gameLoop.stop()

  private def update(): Unit = {
    doHorizontalMove()
    doRotateMove()
    doDownMove()

    updateGame()
    updateView()
  }

  @tailrec
  private def doHorizontalMove(): Unit = horizontalMove match {
    case 0 =>
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

    val cellsOutside = position.x + rotatedFigure.width() - BOARD_WIDTH
    val xPosition = if (cellsOutside > 0) position.x - cellsOutside else position.x

    val yDiffPosition = currentFigure.height() - rotatedFigure.height()
    val newPosition = new Point(xPosition, position.y + yDiffPosition)

    if (!collision(newPosition, rotatedFigure)) {
      currentFigure = rotatedFigure
      position = newPosition
      viewYPos += yDiffPosition * gameInfo.blockHeight
    }
  }


  private def doDownMove(): Unit = if (downMove) {
    @tailrec
    def downMoveRec(): Unit = {
      val newPosition = nextPosition()
      if (!collision(newPosition, currentFigure)) {
        position = newPosition
        viewYPos = position.y * gameInfo.blockHeight
        downMoveRec()
      }
    }

    downMoveRec()
    downMove = false
  }

  private def updateGame(): Unit = {
    viewYPos += levelCalculator.currentLevel
    if (viewYPos / gameInfo.blockHeight < position.y) return

    val newPosition = nextPosition()
    if (!collision(newPosition, currentFigure)) {
      position = newPosition
      return
    }

    addCurrentFigureToBoard()
    destroyLines()

    currentFigure = nextFigure
    nextFigure = MatrixFactory.randFigure()
    notifyNextFigureChanged(nextFigure)

    position = initialPositionFor(currentFigure)
    viewYPos = position.y * gameInfo.blockHeight
    if (collision(position, currentFigure)) {
      gameOver()
    }

  }

  private def destroyLines(): Unit = {
    val newBoard = Array.ofDim[Int](TOTAL_BOARD_HEIGHT, BOARD_WIDTH)

    var y = TOTAL_BOARD_HEIGHT - 1
    for (i <- (TOTAL_BOARD_HEIGHT - 1) until 0 by -1 if !board(i).forall(_ != 0)) {
      newBoard(y) = board(i)
      y -= 1
    }

    board = Matrix(newBoard)
    onLinesDestroyed(linesCount = y, cellsInLine = BOARD_WIDTH)
  }

  private def collision(pos: Point, figure: Matrix): Boolean = {
    for (i <- 0 until figure.height()) {
      for (j <- 0 until figure.width() if figure(i)(j) != 0) {
        val yPos = i + pos.y
        val xPos = j + pos.x

        if (yPos >= TOTAL_BOARD_HEIGHT || yPos < 0) return true
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

    onFigureAdded(currentFigure)
  }

  private def nextPosition() = new Point(position.x, position.y + 1)

  private def initialPositionFor(m: Matrix): Point = {
    val x = (BOARD_WIDTH - m.width()) / 2
    val y = INVISIBLE_BOARD_PART_HEIGHT - m.height() + 1
    new Point(x, y)
  }

  private def gameOver() = {
    updateView()
    gameLoop.stop()
    notifyGameOver()
  }

  private def updateView() = {
    val viewCurrentPosition = new Point(position.x * gameInfo.blockWidth, viewYPos - (INVISIBLE_BOARD_PART_HEIGHT * gameInfo.blockHeight))
    val visibleBoard = board.drop(INVISIBLE_BOARD_PART_HEIGHT)

    notifyWorldChanged(new WorldState(visibleBoard, currentFigure, viewCurrentPosition))
  }

}

object ClassicGame {
  private def calculateMaxFigureHeight(): Int = {
    MatrixFactory.matrixList().
      foldLeft(0)(
        (currentMax, matrix) => math.max(currentMax, math.max(matrix.height(), matrix.width()))
      )
  }
}