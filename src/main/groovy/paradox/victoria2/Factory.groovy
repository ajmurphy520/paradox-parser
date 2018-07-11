package paradox.victoria2

class Factory {

    String factoryType
    Integer factoryLevel
    BigDecimal money
    BigDecimal spending
    BigDecimal income
    BigDecimal paychecks
    Good output
    BigDecimal productionAmount
    BigDecimal leftovers
    boolean underConstruction
    Map<String, BigDecimal> stockpile = new HashMap<>()
    List<EmployedPopulation> employees = new ArrayList<>()

    void addStockpileItem(String item, BigDecimal amount) {
        stockpile.put(item, amount)
    }

    void addEmployees(EmployedPopulation employedPopulation) {
        employees.add(employedPopulation)
    }
}
