import scalafx.scene.shape.Rectangle
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._

import scala.collection.mutable

class Game {
  val column = 10
  val row = 20
  val initialPosX = 4
  val initialPosY = 0
  val emptyFill = White

  val board = mutable.ArrayBuffer.fill[Rectangle](column, row)(null)
  //val map = mutable.ArrayBuffer.fill[Color](column, row)(null)
  var currentBlock:Block = null

  def isMovable(block:Block, dx:Int, dy:Int):Boolean = {
    for (i <- 0 until 4; j <- 0 until 4) {
      val px = block.px + dx + i
      val py = block.py + dy + j
      if (0 <= px && px < 4 && 0 <= py && py < 4
        && block.tiles(px)(py) == true) {
        //continue
      }
      else if (board(px)(py).fill != emptyFill) {
        return false
      }
    }
    return true
  }

  def moveBlock(block:Block, dx:Int, dy:Int) = {
    for (i <- 0 until 4; j <- 0 until 4) {
      val px = block.px + i
      val py = block.py + j
      if (block.tiles(px)(py) == true) {
        board(px)(py).fill = emptyFill
        //map(px)(py) = emptyFill
      }
    }
    block.px += dx
    block.py += dy
    updateBlock(block)
  }

  def updateBlock(block:Block) = {
    for (i <- 0 until 4; j <- 0 until 4) {
      val px = block.px + i
      val py = block.py + j
      if (block.tiles(i)(j) == true)
        board(px)(py).fill = getColor(block.shape)
    }
  }

  def getColor(shape:Int):scalafx.scene.paint.Color = {
    shape match {
      case 0 => {
        Green
      }
      case _ => {
        Pink
      }
    }
  }

  def initialize() = {
    for (i <- 0 until column; j <- 0 until row) {
      val tile = getEmptyTile(i,j)
      //map(i)(j) = emptyFill
      board(i)(j) = tile
    }
    currentBlock = getRandomBlock()
    updateBlock(currentBlock)
  }

  def getEmptyTile():Rectangle = getEmptyTile(0,0)
  def getEmptyTile(column:Int, row:Int):Rectangle = {
    new Rectangle {
      x = 32 * column + 2 * (column + 1)
      y = 32 * row + 2 * (row + 1)
      width = 32
      height = 32
      fill = emptyFill
    }
  }

  def update() = {
    if (isMovable(currentBlock, 0, 1)) {
      moveBlock(currentBlock, 0, 1)
    }
    else {
      System.out.println("not movable");
    }
  }

  def getRandomBlock():Block = {
    val num = scala.util.Random.nextInt(/*7*/1)
    val tiles:Array[Array[Boolean]] = num match {
      case 0 => {
        val tiles = Array.fill(4,4)(false)
        tiles(0)(0) = true
        tiles(0)(1) = true
        tiles(0)(2) = true
        tiles(0)(3) = true
        tiles
      }
    }
    new Block(tiles, num, initialPosX, initialPosY)
  }
}
