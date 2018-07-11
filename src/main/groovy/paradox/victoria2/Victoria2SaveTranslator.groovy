package paradox.victoria2

class Victoria2SaveTranslator {

    Victoria2Config configData

    Victoria2SaveTranslator(Victoria2Config configData) {
        this.configData = configData
    }

    Victoria2Game translateParsedSaveGame(Map<String, Object> saveGame) {
        Victoria2Game gameState = new Victoria2Game()

        translateCountries(gameState, saveGame)

        return gameState
    }

    private void translateCountries(Victoria2Game gameState, Map<String, Object> saveGame) {
        Map<String, Object> countryData = saveGame.findAll {it.key.matches('[A-Z]{3}|(D[0-5][0-9])')}

        countryData.entrySet().each { entry ->
            Country translatedCountry = new Country()
            translatedCountry.tag = entry.key
            gameState.addCountry(translatedCountry)
        }
    }
}
