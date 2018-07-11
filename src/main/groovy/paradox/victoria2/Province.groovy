package paradox.victoria2

class Province {

    String id
    String name
    Country owner
    Map<String, List<Population>> pops = new HashMap<>()
    RGO rgo
}
