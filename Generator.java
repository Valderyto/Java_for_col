package src;
public interface Generator {
    Generator createSomeText(String text);
    Generator createClass(String name, Modificator... modificators);
    Generator createField(String name, Object value, Type type, Modificator... modificators);
    String generate();
}