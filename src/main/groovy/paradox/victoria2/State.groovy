package paradox.victoria2

class State {

    String stateId
    Map<String, Province> stateProvinces = new HashMap<>()
    List<Factory> factories = new ArrayList<>()

    void addProvince(Province province) {

    }

    void addFactory(Factory factory) {
        factories.add(factory)
    }
}
