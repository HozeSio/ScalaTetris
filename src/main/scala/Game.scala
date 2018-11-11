import scalafx.scene.shape.Rectangle
import scalafx.Includes._
import scalafx.beans.property.{IntegerProperty, ObjectProperty, StringProperty}
import javafx.scene.paint.{Color, Paint}
import javafx.scene.paint.Color._

import scala.util.control.Breaks._
import scala.collection.mutable

class Game {
  val column = 10
  val row = 20
  val initialPosX = 4
  val initialPosY = 0
  val emptyFill = rgb(255,255,255)

  val board = mutable.ArrayBuffer.fill[Rectangle](column, row)(null)
  val map = mutable.ArrayBuffer.fill[ObjectProperty[Paint]](column, row)(null)
  var currentBlock:Block = null
  val scoreProperty:StringProperty = StringProperty("0")
  var score = 0

  def isMovable(block:Block, dx:Int, dy:Int):Boolean = {
    for (r <- 0 until 4; c <- 0 until 4) {
      breakable {
        if (block.hasTileAt(c, r) == false) {
          break
        }

        val px = block.px + dx + c
        val py = block.py + dy + r
        if (py >= row || px < 0 || px >= column) {
          return false
        }
        if (0 <= dx + c && dx + c < 4 && 0 <= dy + r && dy + r < 4
          && block.hasTileAt(dx + c, dy + r)) {
          break
        }
        if (map(px)(py).value != emptyFill) {
          return false
        }
      }
    }
    return true
  }

  def moveBlock(block:Block, dx:Int, dy:Int) = {
    for (row <- 0 until 4; col <- 0 until 4) {
      if (block.hasTileAt(col, row)) {
        map(block.px + col)(block.py + row).value = emptyFill
      }
    }
    block.px += dx
    block.py += dy
    updateBlock(block)
  }

  def updateBlock(block:Block) = {
    for (row <- 0 until 4; col <- 0 until 4) {
      if (block.hasTileAt(col, row)) {
        map(block.px + col)(block.py+row).value = block.color
      }
    }
  }

  def initialize() = {
    for (i <- 0 until column; j <- 0 until row) {
      map(i)(j) = ObjectProperty(emptyFill)
      board(i)(j) = new Rectangle {
        x = 32 * i + 2 * (i + 1)
        y = 32 * j + 2 * (j + 1)
        width = 32
        height = 32
        fill <== map(i)(j)
      }
    }
    currentBlock = getRandomBlock()
    updateBlock(currentBlock)
  }

  def update() = {
    while(checkLine()) {

    }
    if (isMovable(currentBlock, 0, 1)) {
      moveBlock(currentBlock, 0, 1)
    }
    else {
      currentBlock = getRandomBlock()
      updateBlock(currentBlock)
    }
  }

  def checkLine():Boolean = {
    for(j <- (row-1) to 0 by -1) {
      var isClearLine = true
      for (i <- 0 until column) {
        if (map(i)(j).value == emptyFill)
          isClearLine = false
      }
      if (isClearLine) {
        removeLine(j)
        return true
      }
    }
    return false;
  }

  def removeLine(row:Int) = {
    for(r <- row to 0 by -1; col <- 0 until column) {
      if (r > 0) {
        map(col)(r).value = map(col)(r - 1).value
      } else {
        map(col)(r).value = emptyFill
      }
    }

    score += 10
    scoreProperty.value = score.toString
  }

  def getRandomBlock():Block = {
    val shape = scala.util.Random.nextInt(/*7*/2)
    new Block(shape, 0, initialPosX, initialPosY)
  }
}
