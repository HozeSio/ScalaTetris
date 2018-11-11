import javafx.scene.paint.Color

class Block(val shape:Int, var dir:Int, var px:Int, var py:Int) {
  var tiles:Int = BlockShape.getBlock(shape, dir)

  def color:Color = BlockShape.color(shape)

  def hasTileAt(col:Int, row:Int):Boolean = {
    BlockShape.hasTileAt(tiles, col, row)
  }

  def changeDir(dir:Int): Unit = {
    this.dir = dir
    tiles = BlockShape.getBlock(shape, dir)
  }
}

object BlockShape {
  private val blocks:Array[Array[Int]] = Array(
    Array(0x0F00, 0x2222, 0x00F0, 0x4444),
    Array(0x44C0, 0x8E00, 0x6440, 0x0E20),
    Array(0x4460, 0x0E80, 0xC440, 0x2E00),
    Array(0xCC00, 0xCC00, 0xCC00, 0xCC00),
    Array(0x06C0, 0x8C40, 0x6C00, 0x4620),
    Array(0x0E40, 0x4C40, 0x4E00, 0x4640),
    Array(0x0C60, 0x4C80, 0xC600, 0x2640)
  )
  val color = Array(
    Color.GREEN,
    Color.BLUE,
    Color.YELLOW,
    Color.ORANGE,
    Color.RED,
    Color.PURPLE,
    Color.CYAN
  )

  def getBlock(shape:Int, dir:Int): Int = {
    blocks(shape)(dir)
  }

  def hasTileAt(shape:Int, col:Int, row:Int):Boolean = {
    ((0x8000 >> (col) >> (row * 4)) & shape) != 0
  }

  def isValidRange(col:Int, row:Int):Boolean = {
    if (col < 0 || col >= 4 || row < 0 || row >= 4)
      return false
    return true
  }
}
