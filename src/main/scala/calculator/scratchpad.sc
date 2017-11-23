import java.nio.file.Paths

import play.api.libs.json._

import scala.io.Source


val fileName = "/Users/je0018/Development/scala/energy-scala/resources/prices.json"

val jsonString = Source
  .fromFile(fileName, "utf8")
  .mkString

val json: JsValue = Json.parse(jsonString)

//val json: JsValue = Json.parse("{\"tariff\": \"better-energy\", \"rates\": {\"power\":  0.1367, \"gas\": 0.0288}, \"standing_charge\": 8.33}")

case class Rates(power: Option[Float], gas: Option[Float])

object Rates {
  implicit val ratesReads = Json.reads[Rates]
}

case class Tariff(tariff: String, rates: Rates, standing_charge: Float)

object Tariff {
  implicit val tariffReads = Json.reads[Tariff]
}

//case class AllTariffs(tariffs: List[Tariff])
//implicit val allTariffReads = Json.reads[AllTariffs]

json.validate[List[Tariff]] match {
  case JsSuccess(tariffs, _) => println(tariffs)
  case JsError(_) => println("parsing failed ")
}
