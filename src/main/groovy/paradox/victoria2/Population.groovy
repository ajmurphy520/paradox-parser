package paradox.victoria2

class Population {

    String type
    String provinceId
    Integer size
    BigDecimal money
    BigDecimal bank
    BigDecimal lifeNeeds
    BigDecimal everydayNeeds
    BigDecimal luxuryNeeds

    Good output
    BigDecimal productionAmount
    BigDecimal leftover
    Map<String, BigDecimal> stockpile = new HashMap<>()
    Map<String, BigDecimal> needs = new HashMap<>()
    BigDecimal spending
    BigDecimal income
    BigDecimal soldDomestic
    BigDecimal soldExport
}
