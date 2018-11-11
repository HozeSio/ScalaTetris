import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import scalafx.event.ActionEvent
import scalafx.Includes._

object main extends JFXApp {

  val g = new Game()
  g.initialize()
  stage = new PrimaryStage {
    title = "ScalaFx Tetris"
    width = 640
    height = 800
    scene = new Scene {
      fill = Black
      content_=(g.board.flatten)
    }
    val timer = new java.util.Timer()
    val task = new java.util.TimerTask {
      override def run(): Unit = {
        System.out.println("Update")
        g.update()
        Platform.runLater {
          System.out.println("runLater")
          scene.value.content_=(g.board.flatten)
        }
      }
    }
    timer.schedule(task, 0, 1000)
  }
}