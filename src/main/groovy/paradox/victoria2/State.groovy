package paradox.victoria2

class State {

    String stateId
    Map<String, Province> stateProvinces = [:]
    List<Factory> factories = []
    BigDecimal savings
    BigDecimal interest
    PopProject popProject
}
