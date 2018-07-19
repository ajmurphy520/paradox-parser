package paradox.parser

import java.util.regex.Pattern

class SaveParser {

    private static Pattern DOUBLE_QUOTE_PATTERN = Pattern.compile('"')

    private Reader reader
    private String currentLine

    private int linesRead = 0

    private SaveParser(Reader reader) {
        this.reader = reader
    }

    static def parseSaveGameFile(File saveGame) {
        saveGame.withReader { reader ->
            SaveParser parser = new SaveParser(reader)
            return parser.parse()
        }
    }

    private def parse() {
        try {
            return parseGameObject()
        } catch (Exception e) {
            println currentLine
            throw e
        } finally {
            println(linesRead)
        }
    }

    private def parseGameObject() {
        readNextNonEmptyLine()
        if (currentLine.contains('=') || currentLine == '}') {
            return parsePropertyObject()
        } else if (currentLine.startsWith('{')) {
            return parseObjectList()
        } else {
            return parseDataList()
        }
    }

    private def parseObjectList() {
        def objects = []
        while (!currentLine.matches('}')) {
            readNextNonEmptyLine()
            objects << parsePropertyObject()
            readNextNonEmptyLine()
        }

        return objects
    }

    private def parseDataList() {
        def data = currentLine.split('\\s+}?|\\}').findAll {it != ''}

        if (!currentLine.endsWith('}')) {
            readNextNonEmptyLine()
            data.addAll(parseDataList())
        }

        return data
    }

    private def parsePropertyObject() {
        def gameObject = new LinkedHashMap()

        while (currentLine != null && !currentLine.matches('}')) {
            parseGameObjectProperty(gameObject)
        }

        return gameObject
    }

    private void parseGameObjectProperty(def gameObject) {
        String[] tokens = currentLine.split('=')

        def propertyValue
        if (currentLine.endsWith('=')) {
            readNextNonEmptyLine()
            propertyValue = parseGameObject()
        } else {
            propertyValue = removeDoubleQuotes(tokens[1])
        }

        setGameObjectProperty(gameObject, removeDoubleQuotes(tokens[0]), propertyValue)
        readNextNonEmptyLine()
    }

    private static String removeDoubleQuotes(String token) {
        return DOUBLE_QUOTE_PATTERN.matcher(token).replaceAll('')
    }

    private static void setGameObjectProperty(def gameObject, String propertyName, Object propertyValue) {
        def currentPropertyValue = gameObject."${propertyName}"
        if (currentPropertyValue == null) {
            currentPropertyValue = propertyValue
        } else if (currentPropertyValue in List.class) {
            currentPropertyValue << propertyValue
        } else {
            currentPropertyValue = [currentPropertyValue]
            currentPropertyValue << propertyValue
        }

        gameObject."${propertyName}" = currentPropertyValue
    }

    private void readNextNonEmptyLine() {
        currentLine = reader.readLine()
        linesRead++
        while (currentLine != null && currentLine.trim() == '') {
            currentLine = reader.readLine()
            linesRead++
        }

        if (currentLine != null) {
            currentLine = currentLine.trim()
        }
    }
}
