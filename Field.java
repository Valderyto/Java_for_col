package src;

public class Field {
    private Modificator modificator;
    private Type type;
    private String name;
    private Object value;
    
    private Field(Builder builder) {
        this.modificator = builder.modificator;
        this.type = builder.type;
        this.name = builder.name;
        this.value = builder.value;
    }
    
    @Override
    public String toString() {
        return modificator + " " + type + " " + name + " = " +
                (type == Type.STRING ? "\"" + value + "\"" : value) + ";";
    }
    
    public static class Builder {
        private Modificator modificator;
        private Type type;
        private String name;
        private Object value;
        
        public Builder setModificator(Modificator modificator) {
            this.modificator = modificator;
            return this;
        }
        
        public Builder setType(Type type) {
            this.type = type;
            return this;
        }
        
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }
        
        // Исключения
        public Field build() throws EmptyNameException {
            if (name == null || name.isEmpty()) {
                throw new EmptyNameException("Имя поля не может быть пустым или null");
            }
            return new Field(this);
        }
    }
}