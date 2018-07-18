package paradox.victoria2

class Country {

    String tag
    BigDecimal money
    BigDecimal bank
    BigDecimal moneyLent
    Map<String, BigDecimal> domesticSupply = [:]
    Map<String, BigDecimal> domesticSold = [:]
    Map<String, BigDecimal> domesticDemand = [:]
    Map<String, BigDecimal> actualSold = [:]
    Map<String, BigDecimal> savedSupply = [:]
    Map<String, BigDecimal> maxBought = [:]
    Map<String, State> states = [:]

    Country(String tag) {
        this.tag = tag
    }

    void addState(State state) {
        states.put(state.stateId, state)
    }

    State getState(String stateId) {
        return states.get(stateId)
    }
}
