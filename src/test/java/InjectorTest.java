package com.sergey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для класса Injector
 * @author Белявцев Сергей
 * @version 1.0
 */
class InjectorTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        // Перед каждым тестом создаем инжектор с тестовой конфигурацией
        injector = new Injector("test_config.properties");
    }

    /**
     * Тест на успешное внедрение зависимостей
     */
    @Test
    void testInject_SuccessfulInjection() throws Exception {
        // Arrange
        SomeBean bean = new SomeBean();

        // Act
        injector.inject(bean);

        // Assert
        // Так как поля приватные, мы используем рефлексию, чтобы проверить их
        Field field1 = SomeBean.class.getDeclaredField("field1");
        field1.setAccessible(true);
        assertNotNull(field1.get(bean), "Поле field1 не должно быть null");
        assertTrue(field1.get(bean) instanceof SomeImpl, "Поле field1 должно быть экземпляром SomeImpl");

        Field field2 = SomeBean.class.getDeclaredField("field2");
        field2.setAccessible(true);
        assertNotNull(field2.get(bean), "Поле field2 не должно быть null");
        assertTrue(field2.get(bean) instanceof SODoer, "Поле field2 должно быть экземпляром SODoer");
    }

    /**
     * Тест на случай, если файл конфигурации не найден
     */
    @Test
    void testInjector_ConfigFileMissing() {
        // Проверяем, что конструктор Injector выбросит исключение,
        // если передать ему имя несуществующего файла
        assertThrows(RuntimeException.class, () -> {
            new Injector("non_existent_file.properties");
        });
    }

    /**
     * Тест на случай, если в конфигурации отсутствует нужная реализация
     */
    @Test
    void testInject_MissingProperty() {
        // Создаем специальный объект для этого теста
        class BeanWithMissingDep {
            @AutoInjectable
            private Runnable missingField; // Интерфейса Runnable нет в нашем test_config.properties
        }

        BeanWithMissingDep bean = new BeanWithMissingDep();

        // Проверяем, что метод inject выбросит исключение
        assertThrows(RuntimeException.class, () -> {
            injector.inject(bean);
        });
    }

    /**
     * Тест для объекта, у которого нет полей для внедрения
     */
    @Test
    void testInject_NoInjectableFields() {
        // Создаем объект без полей @AutoInjectable
        class BeanWithoutInjectables {
            private String someField = "hello";
        }

        BeanWithoutInjectables bean = new BeanWithoutInjectables();

        // Проверяем, что метод inject просто отработает без ошибок
        assertDoesNotThrow(() -> {
            injector.inject(bean);
        });
    }
}