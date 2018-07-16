package paradox.victoria2

class Victoria2SaveTranslator {

    Victoria2Config configData

    Victoria2SaveTranslator(Victoria2Config configData) {
        this.configData = configData
    }

    Victoria2Game processParsedSaveGame(Map<String, Object> saveGame) {
        Victoria2Game gameState = new Victoria2Game()

        processMarketData(gameState, saveGame.worldmarket)
        processProvinces(gameState, saveGame)
        processCountries(gameState, saveGame)

        return gameState
    }
    
    private void processMarketData(Victoria2Game gameState, def worldMarketData) {
        Market market = new Market()
        worldMarketData.price_pool.keySet().each { goodType ->
            market.currentPrice."${goodType}" = parseToBigDecimal(worldMarketData.price_pool."${goodType}")
            market.currentSupply."${goodType}" = parseToBigDecimal(worldMarketData.supply_pool."${goodType}")
            market.currentSupplyWorld."${goodType}" = parseToBigDecimal(worldMarketData.worldmarket_pool."${goodType}")
            market.actualSold."${goodType}" = parseToBigDecimal(worldMarketData.actual_sold."${goodType}")
            market.actualSoldWorld."${goodType}" = parseToBigDecimal(worldMarketData.actual_sold_world."${goodType}")
            market.realDemand."${goodType}" = parseToBigDecimal(worldMarketData.real_demand."${goodType}")
            market.maxDemand."${goodType}" = parseToBigDecimal(worldMarketData.demand."${goodType}")
        }
        gameState.marketData = market
    }

    private void processCountries(Victoria2Game gameState, Map<String, Object> saveGame) {
        configData.countryTags.each { tag ->
            Country currentCountry = gameState.getCountry(tag)
        }
    }

    private void processProvinces(Victoria2Game gameState, Map<String, Object> saveGame) {
        saveGame.findAll {it.key.matches('[0-9]+')}.each { provinceEntry ->
            Province province = new Province(provinceEntry.key)
            province.name = provinceEntry.value.name
            province.owner = gameState.getCountry(provinceEntry.value.owner)
            province.pops = processProvincePopData(provinceEntry.key, provinceEntry.value)
            province.rgo = processProvinceRGO(gameState.marketData, province.pops, provinceEntry.value)
            gameState.addProvince(province)
        }
    }

    private Map<String, List<Population>> processProvincePopData(String provinceId, def provinceData) {
        Map<String, List<Population>> popData = new HashMap<>()

        configData.popTypes.each { popType ->
            popData.put(popType, processPopDataForPopType(provinceId, popType, provinceData."${popType}"))
        }
        return popData
    }

    private List<Population> processPopDataForPopType(String provinceId, String popType, def popData) {
        List<Population> pops = []
        if (popData != null && popData instanceof List) {
            popData.each { popEntry ->
                pops << processSinglePopulation(provinceId, popType, popEntry)
            }
        } else if (popData != null) {
            pops << processSinglePopulation(provinceId, popType, popData)
        }
        return pops
    }

    private Population processSinglePopulation(String provinceId, String popType, def popData) {
        Population singlePop = new Population()
        singlePop.type = popType
        singlePop.provinceId = provinceId
        singlePop.size = parseToInteger(popData.size)
        singlePop.money = parseToBigDecimal(popData.money) / 100.0
        singlePop.bank = parseToBigDecimal(popData.bank) / 100.0
        singlePop.lifeNeeds = parseToBigDecimal(popData.life_needs)
        singlePop.everydayNeeds = parseToBigDecimal(popData.everyday_needs)
        singlePop.luxuryNeeds = parseToBigDecimal(popData.luxury_needs)

        //TODO artisan data

        return singlePop
    }

    private RGO processProvinceRGO(Market marketData, Map<String, List<Population>> provincePops, def provinceData) {
        RGO provinceRGO = new RGO()
        if (provinceData.rgo != null) {
            provinceRGO.outputGood = provinceData.rgo.goods_type
            provinceRGO.productionAmount = parseToBigDecimal(provinceData.rgo.last_income) / marketData.currentPrice."${provinceRGO.outputGood}" / 1000.0
            provinceRGO.employees = processRGOEmployees(provincePops, provinceData.rgo.employment.employees)
        }

        return provinceRGO
    }

    private List<EmployedPopulation> processRGOEmployees(Map<String, List<Population>> provincePops, def employmentData) {
        List<EmployedPopulation> returnResult = []
        employmentData.each { employees ->
            String popType = getPopTypeFromString(employees.province_pop_id.type)
            EmployedPopulation employedPopulation = new EmployedPopulation()
            employedPopulation.population = provincePops."${popType}"[parseToInteger(employees.province_pop_id.index)]
            employedPopulation.numberEmployed = parseToInteger(employmentData.count)
            returnResult << employedPopulation
        }
        return returnResult
    }

    private static Integer parseToInteger(String input) {
        return input != null ? input.toInteger() : 0
    }

    private static BigDecimal parseToBigDecimal(String input) {
        return input != null ? input.toBigDecimal() : 0.0
    }

    private String getPopTypeFromString(String popType) {
        return configData.popTypes[popType.toInteger() - 1]
    }
}
