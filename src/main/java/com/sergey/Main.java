package com.sergey;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Сценарий 1: Используется реализация по умолчанию (SomeImpl) ---");

        // 1. Создаем пустой SomeBean. Его поля field1 и field2 сейчас равны null.
        SomeBean bean1 = new SomeBean();
        System.out.println("SomeBean до инъекции создан.");

        // 2. Создаем нашего "волшебника"-инжектора, который читает файл config.properties.
        Injector injector = new Injector("config.properties");
        System.out.println("Инжектор с конфигурацией по умолчанию создан.");

        // 3. Происходит магия! Инжектор "оживляет" наш bean.
        // После этой строки поля bean1 будут инициализированы объектами SomeImpl и SODoer.
        injector.inject(bean1);
        System.out.println("Зависимости внедрены в SomeBean.");

        // 4. Вызываем метод. Теперь он работает и не бросает NullPointerException.
        System.out.print("Результат вызова foo(): ");
        bean1.foo(); // Ожидаемый вывод: AC
        System.out.println("\n");


        // --- Сценарий 2: Демонстрация гибкости ---
        System.out.println("--- Сценарий 2: Меняем реализацию в config.properties на OtherImpl ---");
        System.out.println("Пожалуйста, откройте файл 'src/main/resources/config.properties',");
        System.out.println("измените строку 'com.sergey.SomeInterface=com.sergey.SomeImpl'");
        System.out.println("на 'com.sergey.SomeInterface=com.sergey.OtherImpl',");
        System.out.println("а затем перезапустите программу.");

        System.out.println("\nЕсли вы это сделаете, то при следующем запуске вывод первого сценария будет 'BC'.");
        System.out.println("Это доказывает, что наше внедрение зависимостей работает и управляется извне.");
    }
}