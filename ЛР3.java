public class Vehicle {
    private String type;          // Тип транспортного средства
    private String color;         // Цвет
    private double speed;         // Скорость (км/ч)
    private boolean hasEngine;    // Есть ли двигатель
    private String engineType;    // Тип двигателя (если имеется)
    private double price;         // Цена
    private String owner;         // Владельцы

    // Конструктор
    public Vehicle(String type, String color, double speed, boolean hasEngine, String engineType, double price, String owner) {
        this.type = type;
        this.color = color;
        this.speed = speed;
        this.hasEngine = hasEngine;
        this.engineType = engineType;
        this.price = price;
        this.owner = owner;
    }

    // Геттеры и сеттеры
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isHasEngine() {
        return hasEngine;
    }

    public void setHasEngine(boolean hasEngine) {
        this.hasEngine = hasEngine;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    // Метод для увеличения скорости
    public void accelerate(double increment) {
        speed += increment;
        System.out.println("Скорость увеличена. Текущая скорость: " + speed + " км/ч");
    }

    // Метод для уменьшения скорости
    public void decelerate(double decrement) {
        if (speed - decrement < 0) {
            speed = 0;
        } else {
            speed -= decrement;
        }
        System.out.println("Скорость уменьшена. Текущая скорость: " + speed + " км/ч");
    }

    // Метод для расчета пройденного расстояния
    public double calculateDistance(double time) { // время в часах
        return speed * time;
    }

    // Метод "газ"
    public void gas(double increment, int seconds) {
        for (int i = 1; i <= seconds; i++) {
            accelerate(increment);
            try {
                Thread.sleep(1000); // Задержка на 1 секунду
            } catch (InterruptedException e) {
                System.out.println("Ошибка в методе 'газ'");
            }
        }
    }

    // Основной метод для тестирования
    public static void main(String[] args) {
        Vehicle car = new Vehicle("Car", "Red", 0, true, "Petrol", 15000, "John Doe");

        System.out.println("Тип: " + car.getType());
        System.out.println("Цвет: " + car.getColor());
        System.out.println("Начальная скорость: " + car.getSpeed() + " км/ч");

        car.accelerate(20);
        car.decelerate(5);

        double distance = car.calculateDistance(2); // Расчет расстояния за 2 часа
        System.out.println("Пройденное расстояние за 2 часа: " + distance + " км");

        System.out.println("Газуем на 5 секунд с ускорением 10 км/ч:");
        car.gas(10, 5);
    }
}

