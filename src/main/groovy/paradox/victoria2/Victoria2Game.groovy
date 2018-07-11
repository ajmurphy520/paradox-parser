package paradox.victoria2

class Victoria2Game {

    private Map<String, Country> countries = new HashMap<>()

    public void addCountry(Country country) {
        countries.put(country.tag, country)
    }

    public Country getCountry(String tag) {
        return countries.get(tag)
    }
}
