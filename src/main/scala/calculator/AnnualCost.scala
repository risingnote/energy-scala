package calculator

case class Usage(power: Int, gas: Int)

case class Result(tariffName: String, cost: Double) {
  override def toString = "  %1$15s ".format(tariffName) + "£%1$,.2f".format(cost)
}

object AnnualCost {

  def calculate(usage: Usage, tariffPrices: List[Tariff]): List[Result] = {

    def tariffCalc(tariff: Tariff): Option[Result] = {
      if (usage.power > 0 && tariff.rates.power.isEmpty ||
        usage.gas > 0 && tariff.rates.gas.isEmpty)
        None
      else {
        val cost: Double = (usage.power.toDouble * tariff.rates.power.getOrElse(0.0) +
          usage.gas.toDouble * tariff.rates.gas.getOrElse(0.0) + tariff.standing_charge * 12) * 1.05
        Some(new Result(tariff.tariff, cost))
      }
    }

    tariffPrices.flatMap(tariffCalc).sortWith((a, b) => b.cost > a.cost)
  }
}
