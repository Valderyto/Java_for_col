package src;

public class Main {
    public static void main(String[] args) {
        Generator generator = new GeneratorJava();
        String result = generator
                .createSomeText("// generated by Vadim")
                .createClass("Main", Modificator.PUBLIC)
                .createField("name", "Vadim", Type.STRING, Modificator.PRIVATE)
                .createField("age", 19, Type.INT, Modificator.PRIVATE)
                .generate();
        System.out.println(result);
        
        try {
            Field field = new Field.Builder()
                    .setModificator(Modificator.PRIVATE)
                    .setType(Type.STRING)
                    .setName("city")
                    .setValue("Volgograd")
                    .build();
            System.out.println(field);
        } catch (EmptyNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}