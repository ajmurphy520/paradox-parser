package paradox.victoria2

class Flags {

    Set<String> flags = new HashSet<>()

    void addFlag(String flagName) {
        flags.add(flagName)
    }

    boolean hasFlag(String flagName) {
        return flags.contains(flagName)
    }

    void removeFlag(String flagName) {
        flags.remove(flagName)
    }
}
