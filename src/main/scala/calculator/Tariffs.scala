/**
  * Load tariff prices from JSON file into List of Tariff objects.
  */
package calculator

import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

import scala.io.Source

case class Rates(power: Option[Double], gas: Option[Double]) {
  def getForEnergyType(energyType: String): Option[Double] = {
    energyType match {
      case "power" => power
      case "gas" => gas
      case "_" => None
    }
  }
}

object Rates {
  implicit val ratesReads = Json.reads[Rates]
}

case class Tariff(tariff: String, rates: Rates, standing_charge: Double)

object Tariff {
  implicit val tariffReads = Json.reads[Tariff]
}

object Tariffs {
  def parsePrices(fileName: String): List[Tariff] = {

    val jsonString = Source
      .fromFile(fileName, "utf8")
      .mkString

    val json: JsValue = Json.parse(jsonString)

    json.validate[List[Tariff]] match {
      case JsSuccess(tariffs, _) => tariffs
      case JsError(_) => {
        println("parsing of " + fileName + " failed ");
        List()
      }
    }
  }
}
