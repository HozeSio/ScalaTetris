import scalafx.scene.shape.Rectangle
import scalafx.Includes._
import scalafx.beans.property.{ObjectProperty, StringProperty}
import javafx.scene.paint.{Color, Paint}

class Game {
  val column = 10
  val row = 20
  val initialPosX = 4
  val initialPosY = 0
  val emptyFill = Color.LIGHTGRAY

  val board = Array.fill[Rectangle](column, row)(null)
  val map = Array.fill[ObjectProperty[Paint]](column, row)(null)
  var currentBlock:Block = _
  val scoreProperty:StringProperty = StringProperty("0")
  var score = 0

  def initialize(): Unit = {
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
    setScore(0)
    spawnBlock()
  }

  def setScore(score:Int): Unit = {
    this.score = score
    scoreProperty.value = s"Score : $score"
  }

  def isMapEmptyAt(col:Int, row:Int):Boolean = {
    map(col)(row).value == emptyFill
  }

  def isMovable(block:Block, dx:Int, dy:Int): Boolean = isMovable(block, dx, dy, block.dir)
  def isMovable(block:Block, dx:Int, dy:Int, dir:Int):Boolean = {
    val destBlock = new Block(block.shape, dir, block.px + dx, block.py + dy)
    for (r <- 0 until 4; c <- 0 until 4) {
      val targetTileX = destBlock.px + c
      val targetTileY = destBlock.py + r
      if (destBlock.hasTileAt(c,r)
          && (targetTileX < 0 || targetTileX >= column || targetTileY < 0 || targetTileY >= row)) {
        return false
      }
      if (destBlock.hasTileAt(c,r) && !isMapEmptyAt(targetTileX, targetTileY)) {
        if (!BlockShape.isValidRange(targetTileX - block.px, targetTileY - block.py)
            || !block.hasTileAt(targetTileX - block.px, targetTileY - block.py)) {
          return false
        }
      }
    }
    return true
  }

  def moveBlock(block:Block, dx:Int, dy:Int): Unit = moveBlock(block, dx, dy, block.dir)
  def moveBlock(block:Block, dx:Int, dy:Int, dir:Int): Unit = {
    for (row <- 0 until 4; col <- 0 until 4) {
      if (block.hasTileAt(col, row)) {
        map(block.px + col)(block.py + row).value = emptyFill
      }
    }
    block.px += dx
    block.py += dy
    block.changeDir(dir)
    updateBlock(block)
  }

  def updateBlock(block:Block): Unit = {
    for (row <- 0 until 4; col <- 0 until 4) {
      if (block.hasTileAt(col, row)) {
        map(block.px + col)(block.py+row).value = block.color
      }
    }
  }

  def update(): Unit = {
    if (isMovable(currentBlock, 0, 1)) {
      moveBlock(currentBlock, 0, 1)
    }
    else {
      checkLine()
      spawnBlock()
    }
  }

  def spawnBlock(): Unit = {
    currentBlock = getRandomBlock()
    updateBlock(currentBlock)
  }

  def checkLine():Unit = {
    for(j <- (row-1) to 0 by -1) {
      var isClearLine = true
      for (i <- 0 until column) {
        if (map(i)(j).value == emptyFill)
          isClearLine = false
      }
      if (isClearLine) {
        removeLine(j)
        checkLine()
      }
    }
  }

  def removeLine(row:Int): Unit = {
    for(r <- row to 0 by -1; col <- 0 until column) {
      if (r > 0) {
        map(col)(r).value = map(col)(r - 1).value
      } else {
        map(col)(r).value = emptyFill
      }
    }
    currentBlock.py += 1
    setScore(score + 10)
  }

  def getRandomBlock():Block = {
    val shape = scala.util.Random.nextInt(7)
    new Block(shape, 0, initialPosX, initialPosY)
  }
}
