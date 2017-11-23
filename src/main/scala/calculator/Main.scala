package calculator

import java.nio.file.Paths

import scala.io.Source

object Main extends App {
  val cmdFile = Paths.get(System.getProperty("user.dir"), "resources/input.txt")
  val pricesFile = Paths.get(System.getProperty("user.dir"), "resources/prices.json")

  val tariffPrices = prices.parsePrices(pricesFile.toString)
  println(tariffPrices)

  Source
    .fromFile(cmdFile.toString, "utf8")
    .getLines
    .map { line =>
      val costPattern = "cost\\s+(\\d+)\\s+(\\d+)".r
      val usagePattern = "usage\\s+(.+)\\s+(.+)\\s+(\\d+)".r
      line match {
        case costPattern(power, gas) => List(power, gas)
        case usagePattern(tariffName, energyType, mthlySpend) => List(tariffName, energyType, mthlySpend)
        case _ => "Unknown command " + line
      }
    }
    .foreach(println)
}
