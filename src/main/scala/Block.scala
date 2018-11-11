import javafx.scene.paint.Color
import javafx.scene.paint.Color.rgb

class Block(val shape:Int, var px:Int, var py:Int) {
  val tiles:Array[Array[Boolean]] = Array.fill(4,4)(false)
  val t = 0x22
  shape match {
    //I
    case 0 => {
      tiles(0)(0) = true
      tiles(0)(1) = true
      tiles(0)(2) = true
      tiles(0)(3) = true
    }
    //j
    case 1 => {
      tiles(0)(2) = true
      tiles(1)(0) = true
      tiles(1)(1) = true
      tiles(1)(2) = true
    }
  }

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
}
