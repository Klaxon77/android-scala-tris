package com.klaxon.tris.common

/**
 * Matrix class
 */
class Matrix(val arr: Array[Array[Int]]) {

  def this(width: Int, height: Int) = this(Array.ofDim[Int](width, height))

  def apply(row: Int) = arr(row)

  def width() = arr.length

  def height() = {
    if (arr.isEmpty) 0
    else arr(0).length
  }

  def isEmpty = arr.isEmpty

  def rotateRight(): Matrix = {
    val newHeight = width()
    val newWidth = height()
    val newArr = Array.ofDim[Int](newWidth, newHeight)

    for (i <- 0 until newWidth) {
      for (j <- 0 until newHeight) {
        newArr(i)(j) = arr(j)(newWidth - 1 - i)
      }
    }

    Matrix(newArr)
  }

  override def toString = {
    arr.deep.mkString("\n")
  }

}

object Matrix {
  def apply(arr: Array[Array[Int]]) = new Matrix(arr)

  def emptyMatrix() = new Matrix(Array.ofDim[Int](0, 0))
}