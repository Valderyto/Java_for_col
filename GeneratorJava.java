package src;
public class GeneratorJava implements Generator {
    private StringBuilder code = new StringBuilder();
    
    @Override
    public Generator createSomeText(String text) {
        code.append(text).append("\n");
        return this;
    }
    
    @Override
    public Generator createClass(String name, Modificator... modificators) {
        for (Modificator mod : modificators) {
            code.append(mod.toString()).append(" ");
        }
        code.append("class ").append(name).append(" {\n");
        return this;
    }

    @Override
    public Generator createField(String name, Object value, Type type, Modificator... modificators) {
        for (Modificator mod : modificators) {
            code.append(mod.toString()).append(" ");
        }
        code.append(type.toString()).append(" ").append(name).append(" = ");
        if (type == Type.STRING) {
            code.append("\"").append(value).append("\"");
        } else {
            code.append(value);
        }
        code.append(";\n");
        return this;
    }
    
    @Override
    public String generate() {
        code.append("}\n");
        return code.toString();
    }
}
