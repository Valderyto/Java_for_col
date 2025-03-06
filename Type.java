package src;
enum Type {
    INT("int"), STRING("String");
    private final String value;
    Type(String value) { this.value = value; }
    @Override public String toString() { return value; }
}