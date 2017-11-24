/**
  * Give tariff and monthly spend calculate annual energy usage.
  */
package calculator

case class UsageResult(annualKwh: Double) {
  override def toString = "  %1$,.0f kwh".format(annualKwh)
}

object AnnualUsage {
  def calculate(tariffName: String, energyType: String, mthlySpend: Double,
                tariffPrices: List[Tariff]): List[UsageResult] = {
    tariffPrices.filter(tariff => tariff.tariff == tariffName &&
      tariff.rates.getForEnergyType(energyType).isDefined).
      map(tariff => {
        UsageResult((mthlySpend / 1.05 - tariff.standing_charge) * 12 /
          tariff.rates.getForEnergyType(energyType).get)
      })
  }
}
