package com.sergey;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс, отвечающий за внедрение зависимостей в объекты.
 * Использует рефлексию для анализа полей объекта и файл .properties для конфигурации.
 *
 * @author Белявцев Сергей
 * @version 1.0
 */
public class Injector {

    /**
     * Хранилище для конфигурации, загруженной из .properties файла.
     */
    private final Properties properties;

    /**
     * Создает инжектор и загружает конфигурацию из указанного файла.
     * @param configPath Путь к файлу .properties в папке resources.
     */
    public Injector(String configPath) {
        properties = new Properties();
        // Используем try-with-resources для автоматического закрытия потока
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(configPath)) {
            if (in == null) {
                // Если файл не найден, выбрасываем ошибку
                throw new IOException("Файл конфигурации не найден: " + configPath);
            }
            // Загружаем пары "ключ=значение" из файла в наш объект properties
            properties.load(in);
        } catch (IOException e) {
            // Если произошла ошибка чтения, сообщаем об этом
            throw new RuntimeException("Не удалось загрузить файл конфигурации.", e);
        }
    }

    /**
     * Внедряет зависимости в поля объекта, помеченные аннотацией @AutoInjectable.
     * @param object Объект любого типа, в который нужно внедрить зависимости.
     * @param <T> Тип объекта.
     * @return Тот же самый объект, но с уже инициализированными полями.
     */
    public <T> T inject(T object) {
        try {
            // 1. Получаем все поля класса объекта, включая private.
            Field[] fields = object.getClass().getDeclaredFields();

            // 2. Проходим по каждому полю
            for (Field field : fields) {
                // 3. Проверяем, есть ли на поле наша аннотация @AutoInjectable
                if (field.isAnnotationPresent(AutoInjectable.class)) {

                    // 4. Получаем тип поля. Это будет интерфейс (например, SomeInterface.class)
                    Class<?> interfaceType = field.getType();

                    // 5. Ищем в файле properties имя класса-реализации по имени интерфейса.
                    // interfaceType.getName() вернет полное имя, например, "com.sergey.SomeInterface"
                    String implementationName = properties.getProperty(interfaceType.getName());
                    if (implementationName == null) {
                        throw new RuntimeException("Не найдена реализация для интерфейса " + interfaceType.getName() + " в файле конфигурации.");
                    }

                    // 6. С помощью рефлексии создаем экземпляр класса-реализации.
                    // Сначала получаем Class объект по имени
                    Class<?> implementationClass = Class.forName(implementationName);
                    // Затем создаем пустой экземпляр (вызываем конструктор без аргументов)
                    Object instance = implementationClass.getDeclaredConstructor().newInstance();

                    // 7. Делаем приватное поле ДОСТУПНЫМ для изменения. Без этого будет ошибка.
                    field.setAccessible(true);

                    // 8. Внедряем созданный объект (instance) в поле (field) нашего объекта (object).
                    field.set(object, instance);
                }
            }
        } catch (Exception e) {
            // Все возможные ошибки рефлексии (класс не найден, нет конструктора и т.д.)
            // мы "оборачиваем" в одну общую ошибку времени выполнения.
            throw new RuntimeException("Не удалось внедрить зависимость.", e);
        }
        return object;
    }
}