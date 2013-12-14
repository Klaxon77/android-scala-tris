package com.klaxon.tris.common


/**
 * Matrix class
 */
class Matrix(arr: Array[Array[Int]]) {

  def this(height: Int, width: Int) = this(Array.ofDim[Int](height, width))

  def apply(row: Int) = arr(row)

  def width() = {
    if (arr.isEmpty) 0
    else arr(0).length
  }

  def height() = arr.length

  def isEmpty = arr.isEmpty

  def rotateRight(): Matrix = {
    val newHeight = width()
    val newWidth = height()
    val newArr = Array.ofDim[Int](newHeight, newWidth)

    for (i <- 0 until newHeight) {
      for (j <- 0 until newWidth) {
        newArr(i)(j) = arr(newWidth - 1 - j)(i)
      }
    }

    Matrix(newArr)
  }

  def array = arr

  override def toString = {
    arr.deep.mkString("\n")
  }

}

object Matrix {
  def apply(arr: Array[Array[Int]]) = new Matrix(arr)
  def apply(height: Int, width: Int) = new Matrix(height, width)

  def emptyMatrix() = new Matrix(Array.ofDim[Int](0, 0))
}