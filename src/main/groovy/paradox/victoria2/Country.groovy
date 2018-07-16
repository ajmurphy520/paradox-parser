package paradox.victoria2

class Country {

    String tag
    Flags flags
    BigDecimal money
    Map<String, State> states = new HashMap<>()

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
