package paradox.victoria2

import paradox.parser.SaveParser
import spock.lang.Shared
import spock.lang.Specification

class Victoria2TranslatorSpec extends Specification {

    @Shared
    def saveGame

    void setupSpec() {
        File saveFile = new File(Victoria2TranslatorSpec.classLoader.getResource('paradox/parser/Sweden1844_11_28.v2').file)
        saveGame = SaveParser.parseSaveGameFile(saveFile)
    }

    void "should parse country data"() {
        given:
        Victoria2SaveTranslator translator = new Victoria2SaveTranslator()

        when:
        Victoria2Game gameState = translator.translateParsedSaveGame(saveGame)

        then:
        gameState.countries.size() == 271
    }
}
