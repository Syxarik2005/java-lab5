package com.sergey;

/**
 * Вторая, альтернативная реализация интерфейса {@link SomeInterface}
 * Используется для демонстрации гибкости DI, когда реализацию можно
 * поменять в файле конфигурации
 *
 * @author Белявцев Сергей
 * @version 1.0
 */
public class OtherImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.print("B");
    }
}