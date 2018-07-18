package paradox.victoria2

import paradox.parser.SaveParser
import spock.lang.Shared
import spock.lang.Specification

class Victoria2TranslatorSpec extends Specification {

    @Shared
    def saveGame

    @Shared
    Victoria2Config configData

    void setupSpec() {
        File saveFile = new File(Victoria2TranslatorSpec.classLoader.getResource('paradox/parser/Sweden1844_11_28.v2').file)
        saveGame = SaveParser.parseSaveGameFile(saveFile)

        configData = buildConfigData()
    }

    void "should process country data"() {
        given:
        Victoria2SaveTranslator translator = new Victoria2SaveTranslator(configData)

        when:
        Victoria2Game gameState = translator.processParsedSaveGame(saveGame)

        then:
        gameState.countries.size() == 271
    }

    void "should process province data"() {
        given:
        Victoria2SaveTranslator translator = new Victoria2SaveTranslator(configData)

        when:
        Victoria2Game gameState = translator.processParsedSaveGame(saveGame)

        then:
        gameState.provinces.size() == 3248
//        gameState.marketData.currentSupply.each { goodSupply ->
//            BigDecimal rgoOutput = 0.0
//            gameState.provinces.findAll {it.value.rgo != null && it.value.rgo.outputGood == goodSupply.key}.each { province ->
//                goodSupply.value -= province.value.rgo.productionAmount
//                rgoOutput += province.value.rgo.productionAmount
//            }
//            goodSupply.value <= 1.0
//            goodSupply.value >=0.0
//            println "$goodSupply.key $goodSupply.value $rgoOutput ${gameState.marketData.actualSold.get(goodSupply.key)} ${gameState.marketData.actualSoldWorld.get(goodSupply.key)}"
//        }

//        gameState.marketData.currentSupply.each { goodSupply ->
//            def goodKey = goodSupply.key
//            print goodKey + '\t'
//            print goodSupply.value + '\t'
//            print gameState.marketData.currentSupplyWorld."${goodKey}" + '\t'
//            print gameState.marketData.actualSold."${goodKey}" + '\t'
//            print gameState.marketData.actualSoldWorld."${goodKey}" + '\t'
//            print gameState.marketData.realDemand."${goodKey}" + '\t'
//            println gameState.marketData.maxDemand."${goodKey}" + '\t'
//        }
//
//        BigDecimal moneySum = 0.0
//        BigDecimal bankSum = 0.0
//        int count = 0
//        gameState.provinces.each { provinceEntry ->
//            provinceEntry.value.pops.each { popsEntry ->
//                popsEntry.value.each { singlePop ->
//                    moneySum += singlePop.money
//                    bankSum += singlePop.bank
//                    count++
//                }
//            }
//        }
//        println count
//        println moneySum
//        println bankSum
    }

    static Victoria2Config buildConfigData() {
        Victoria2Config config = new Victoria2Config()
        config.countryTags = ['REB', 'ENG', 'RUS', 'FRA', 'PRU', 'GER', 'AUS', 'USA', 'JAP', 'ITA', 'KUK', 'DEN', 'FIN', 'NOR', 'SCA', 'SWE', 'BEL', 'CAT', 'FLA', 'IRE', 'NET', 'POR', 'SCO', 'SPA', 'WLL', 'ANH', 'BAD', 'BAV', 'BRA', 'BRE', 'COB', 'DZG', 'FRM', 'HAM', 'HAN', 'HES', 'HEK', 'HOL', 'LIP', 'LUB', 'LUX', 'MEC', 'MEI', 'NAS', 'NGF', 'OLD', 'SAX', 'SCH', 'SGF', 'WEI', 'WUR', 'LOM', 'LUC', 'MOD', 'PAP', 'PAR', 'SAR', 'SIC', 'TRE', 'TUS', 'VEN', 'BOH', 'KRA', 'CRI', 'EST', 'HUN', 'LIT', 'LAT', 'POL', 'SLV', 'SWI', 'RUT', 'SIE', 'UKR', 'CZH', 'ALB', 'BOS', 'BUL', 'CRE', 'CRO', 'CYP', 'GRE', 'ION', 'MOL', 'MON', 'ROM', 'SER', 'SLO', 'TUR', 'WAL', 'YUG', 'ALD', 'CNG', 'EGY', 'ETH', 'MAD', 'LIB', 'MOR', 'NAL', 'ORA', 'SAF', 'SOK', 'RHO', 'TRI', 'TRN', 'TUN', 'ZAN', 'ZUL', 'ABU', 'AFG', 'ARM', 'AZB', 'BUK', 'GEO', 'HDJ', 'IRQ', 'KHI', 'KOK', 'MUG', 'NEJ', 'OMA', 'PER', 'YEM', 'HND', 'AWA', 'BAS', 'BER', 'BHO', 'BIK', 'BUN', 'GWA', 'HYD', 'IND', 'JAI', 'JAS', 'JOD', 'KAL', 'KAS', 'KUT', 'LAD', 'MAK', 'MEW', 'MYS', 'NAG', 'NEP', 'ORI', 'PAN', 'SHI', 'SIK', 'SIN', 'TRA', 'ATJ', 'BAL', 'BHU', 'BRU', 'BUR', 'CAM', 'CHI', 'DAI', 'JAV', 'KOR', 'LUA', 'JOH', 'SIA', 'TIB', 'MGL', 'WIA', 'PHI', 'MCK', 'GXI', 'SXI', 'YNN', 'XBI', 'XIN', 'CAL', 'CAN', 'CHE', 'COL', 'COS', 'CSA', 'CUB', 'DES', 'DOM', 'ELS', 'GUA', 'HAI', 'HAW', 'MAN', 'MEX', 'MTC', 'HON', 'NEN', 'NEW', 'NIC', 'PNM', 'QUE', 'TEX', 'UCA', 'ARG', 'BOL', 'BRZ', 'CHL', 'CLM', 'ECU', 'PEU', 'PRG', 'URU', 'VNZ', 'AST', 'NZL', 'ISR', 'BYZ', 'ICL', 'BAB', 'TPG', 'ARA', 'UBD', 'BYE', 'PLC', 'GCO', 'JAN', 'D01', 'D02', 'D03', 'D04', 'D05', 'D06', 'D07', 'D08', 'D09', 'D10', 'D11', 'D12', 'D13', 'D14', 'D15', 'D16', 'D17', 'D18', 'D19', 'D20', 'D21', 'D22', 'D23', 'D24', 'D25', 'D26', 'D27', 'D28', 'D29', 'D30', 'D31', 'D32', 'D33', 'D34', 'D35', 'D36', 'D37', 'D38', 'D39', 'D40', 'D41', 'D42', 'D43', 'D44', 'D45', 'D46', 'D47', 'D48', 'D49', 'D50']
        config.popTypes = ['aristocrats', 'artisans', 'bureaucrats', 'capitalists', 'clergymen', 'clerks', 'craftsmen', 'farmers',
        'labourers', 'officers', 'slaves', 'soldiers']
        config.goodTypes = ['ammunition':'ammunition', 'small_arms':'small_arms', 'artillery':'artillery', 'canned_food':'canned_food', 'barrels':'barrels', 'aeroplanes':'aeroplanes', 'cotton':'cotton', 'dye':'dye', 'wool':'wool', 'silk':'silk', 'coal':'coal', 'sulphur':'sulphur', 'iron':'iron', 'timber':'timber', 'tropical_wood':'tropical_wood', 'rubber':'rubber', 'oil':'oil', 'precious_metal':'precious_metal', 'steel':'steel', 'cement':'cement', 'machine_parts':'machine_parts', 'glass':'glass', 'fuel':'fuel', 'fertilizer':'fertilizer', 'explosives':'explosives', 'clipper_convoy':'clipper_convoy', 'steamer_convoy':'steamer_convoy', 'electric_gear':'electric_gear', 'fabric':'fabric', 'lumber':'lumber', 'paper':'paper', 'cattle':'cattle', 'fish':'fish', 'fruit':'fruit', 'grain':'grain', 'tobacco':'tobacco', 'tea':'tea', 'coffee':'coffee', 'opium':'opium', 'automobiles':'automobiles', 'telephones':'telephones', 'wine':'wine', 'liquor':'liquor', 'regular_clothes':'regular_clothes', 'luxury_clothes':'luxury_clothes', 'furniture':'furniture', 'luxury_furniture':'luxury_furniture', 'radio':'radio']
        config.factoryTypes = [:]
        return config
    }
}
