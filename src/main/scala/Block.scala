import javafx.scene.paint.Color
import javafx.scene.paint.Color.rgb

class Block(val shape:Int, val dir:Int, var px:Int, var py:Int) {
  var tiles:Int = BlockShape.getBlock(shape, dir)

  def color:Color = {
    shape match {
      case 0 => {
        rgb(0,255,0)
      }
      case _ => {
        rgb(122,122,0)
      }
    }
  }

  def hasTileAt(col:Int, row:Int):Boolean = {
    ((0x8000 >> (col) >> (row * 4)) & tiles) != 0
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

  def getBlock(shape:Int, dir:Int): Int = {
    blocks(shape)(dir)
  }
}
