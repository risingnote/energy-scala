package calculator

import java.nio.file.Paths

import scala.io.Source

object Main extends App {
  val cmdFile = Paths.get(System.getProperty("user.dir"), "resources/input.txt")
  val pricesFile = Paths.get(System.getProperty("user.dir"), "resources/prices.json")

  val tariffPrices = Tariffs.parsePrices(pricesFile.toString)

  Source
    .fromFile(cmdFile.toString, "utf8")
    .getLines
    .map { line =>
      val costPattern = "cost\\s+(\\d+)\\s+(\\d+)".r
      val usagePattern = "usage\\s+(.+)\\s+(.+)\\s+(\\d+)".r
      line match {
        case costPattern(power, gas) => line ::
          AnnualCost.calculate(new Usage(power.toInt, gas.toInt), tariffPrices)
        case usagePattern(tariffName, energyType, mthlySpend) =>
          List(tariffName, energyType, mthlySpend)
        case _ => List("Unknown command " + line)
      }
    }
    .foreach(x => x.map(println))
}
