/**
  * Given energy usage, determine suitable tariffs cheapest first.
  */
package calculator

case class CostResult(tariffName: String, cost: Double) {
  override def toString = "  %1$-15s ".format(tariffName) +
    "£%1$,.2f".format(cost)
}

object AnnualCost {

  def calculate(powerUsage: Int, gasUsage: Int, tariffPrices: List[Tariff])
  : List[CostResult] = {

    def tariffCalc(tariff: Tariff): Option[CostResult] = {
      if (powerUsage > 0 && tariff.rates.power.isEmpty ||
        gasUsage > 0 && tariff.rates.gas.isEmpty)
        None
      else {
        val cost: Double = (powerUsage.toDouble * tariff.rates.power
          .getOrElse(0.0) +
          gasUsage.toDouble * tariff.rates.gas.getOrElse(0.0) + tariff
          .standing_charge * 12) * 1.05
        Some(CostResult(tariff.tariff, cost))
      }
    }

    // Unsuitable tariffs (None) will be removed by flatMap
    tariffPrices.flatMap(tariffCalc).sortWith((a, b) => b.cost > a.cost)
  }
}
