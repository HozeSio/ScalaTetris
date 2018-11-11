import java.beans.EventHandler

import javafx.scene.input.KeyCode
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import scalafx.Includes._
import scalafx.beans.property.IntegerProperty

object main extends JFXApp {

  val g = new Game()
  g.initialize()
  stage = new PrimaryStage {
    title = "ScalaFx Tetris"
    width = 640
    height = 800
    scene = new Scene {
      fill = Black
      content = g.board.flatten
      val text = new HBox() {
        layoutX = 530
        layoutY = 20
        children = new Text() {
          text <== g.scoreProperty
          style = "-fx-font-size: 32pt"
          fill = White
        }
      }
      content.add(text)
      onKeyPressed.value = ke => {
        ke.getCode match {
          case KeyCode.LEFT => {
            if (g.isMovable(g.currentBlock,-1,0))
              g.moveBlock(g.currentBlock,-1, 0)
          }
          case KeyCode.RIGHT => {
            if (g.isMovable(g.currentBlock,1,0))
              g.moveBlock(g.currentBlock,1,0)
          }
          case KeyCode.DOWN => {
            if (g.isMovable(g.currentBlock,0,1))
              g.moveBlock(g.currentBlock,0,1)
          }
          case KeyCode.UP => {
            if (g.isMovable(g.currentBlock, 0, 0, (g.currentBlock.dir + 1) % 4))
              g.moveBlock(g.currentBlock, 0, 0, (g.currentBlock.dir + 1) % 4)
          }
          case _ => {

          }
        }
      }
    }
    val timer = new java.util.Timer()
    val task = new java.util.TimerTask {
      override def run(): Unit = {
        g.update()
      }
    }
    timer.schedule(task, 0, 1000)
  }
}