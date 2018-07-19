package paradox.parser

import spock.lang.Ignore
import spock.lang.Specification

import java.lang.reflect.Array

class SaveParserSpec extends Specification {

    File testFile = createTempTestFile()

    void "should parse global variable"() {
        given:
        testFile << 'government=7'

        expect:
        SaveParser.parseSaveGameFile(testFile).government == '7'
    }

    void "should parse double quoted global variable"() {
        given:
        testFile << 'player="SWE"'

        expect:
        SaveParser.parseSaveGameFile(testFile).player == 'SWE'
    }

    void "should parse date global variable"() {
        given:
        testFile << 'date="1836.1.1"'

        expect:
        SaveParser.parseSaveGameFile(testFile).date == '1836.1.1'
    }

    void "should parse multiple global variables"() {
        given:
        testFile << 'rebel=123\n'
        testFile << 'date="1836.1.1"\n'
        testFile << 'unit=4321\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.rebel == '123'
        saveGame.unit == '4321'
        saveGame.date == '1836.1.1'
    }

    void "should parse global game object variable"() {
        given:
        testFile << 'flags=\n'
        testFile << '{\n'
        testFile << '\tthe_great_trek=yes\n'
        testFile << '}\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.flags.the_great_trek == 'yes'
    }

    void "should parse data array object"() {
        given:
        testFile << 'setgameplayoptions=\n'
        testFile << '{\n'
        testFile << '2\t0\t0.1\t-1\t}\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.setgameplayoptions == ['2', '0', '0.1', '-1']
    }

    void "should parse multiple instances of property as a List"() {
        given:
        testFile << 'property=123\n'
        testFile << 'property=456\n'
        testFile << 'property=789\n'

        expect:
        SaveParser.parseSaveGameFile(testFile).property == ['123', '456', '789']
    }

    void "should parse empty data maps"() {
        given:
        testFile << 'overseas_penalty=\n'
        testFile << '{\n'
        testFile << '}\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.overseas_penalty == [:]
    }

    void "should handle extra whitespace in data arrays"() {
        given:
        testFile << 'overseas_penalty=\n'
        testFile << '{\n'
        testFile << '  -1\t}\n'

        expect:
        SaveParser.parseSaveGameFile(testFile).overseas_penalty == ['-1']
    }

    void "should skip empty lines"() {
        given:
        testFile << 'date=1836.1.1\n'
        testFile << '\n'
        testFile << 'government=7\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.date == '1836.1.1'
        saveGame.government == '7'
    }

    void "should handle object lists"() {
        given:
        testFile << 'property=\n'
        testFile << '{\n\n'
        testFile << '\t{\n'
        testFile << '\t\tproperty1=someValue\n'
        testFile << '\t\tproperty2=someValue2\n'
        testFile << '\t}\n\n'
        testFile << '\t{\n'
        testFile << '\t\tproperty1=someValue3\n'
        testFile << '\t\tproperty2=someValue4\n'
        testFile << '\t}\n'
        testFile << '}\n'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.property[0].property1 == 'someValue'
        saveGame.property[0].property2 == 'someValue2'
        saveGame.property[1].property1 == 'someValue3'
        saveGame.property[1].property2 == 'someValue4'
    }

    void "should ignore comments"() {
        given:
        testFile << 'property=someValue  #this is a comment'

        when:
        def saveGame = SaveParser.parseSaveGameFile(testFile)

        then:
        saveGame.property == 'someValue'
    }

    private static File createTempTestFile() {
        File testFile = File.createTempFile('test', '.txt')
        testFile.deleteOnExit()
        return testFile
    }
}
