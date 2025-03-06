package src;
enum Modificator {
    PUBLIC("public"), PRIVATE("private");
    private final String value;
    Modificator(String value) { this.value = value; }
    @Override public String toString() { return value; }
}
