package paradox.victoria2

class Victoria2Game {

    Market marketData
    Map<String, Country> countries = [:]
    Map<String, Province> provinces = [:]
    Map<String, Country> sphereMembership = [:]

    void addCountry(Country country) {
        countries.put(country.tag, country)
    }

    Country getCountry(String tag) {
        Country country = countries.get(tag)
        if (country == null && tag != null) {
            country = new Country(tag)
            countries.put(tag, country)
        }
        return country
    }

    void addProvince(Province province) {
        provinces.put(province.id, province)
    }
}
