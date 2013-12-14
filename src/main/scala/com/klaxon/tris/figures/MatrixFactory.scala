package com.klaxon.tris.figures

import scala.util.Random
import com.klaxon.tris.common.Matrix
import android.util.Log


/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/3/13</p>
 */
object MatrixFactory {

  val matrices = Matrices.getMatrices
  val random = new Random()
  val MAX_ROTATIONS_FOR_UNIQUE_FIGURE = 4

  def randFigure(): Matrix = {
    var matrix = matrices(random.nextInt(matrices.size))

    matrix = rotateRandomly(matrix)
    matrix
  }

  def rotateRandomly(m: Matrix): Matrix = {
    var rotatedMatrix = m

    val rotationCount = random.nextInt(MAX_ROTATIONS_FOR_UNIQUE_FIGURE)
    for (i <- 0 until rotationCount) rotatedMatrix = rotatedMatrix.rotateRight()

    rotatedMatrix
  }

}

private object Matrices {

  val I = Array(
    Array(1, 1, 1, 1)
  )

  val L = Array(
    Array(2, 2, 2),
    Array(2, 0, 0)
  )

  val J = Array(
    Array(3, 0, 0),
    Array(3, 3, 3)
  )

  val S = Array(
    Array(0, 4, 4),
    Array(4, 4, 0)
  )

  val Z = Array(
    Array(5, 5, 0),
    Array(0, 5, 5)
  )

  val T = Array(
    Array(0, 6, 0),
    Array(6, 6, 6)
  )

  val O = Array(
    Array(7, 7),
    Array(7, 7)
  )

  def getMatrices: List[Matrix] = {
    List(I, L, J ,S, Z, T, O).map(Matrix(_))
  }

}


