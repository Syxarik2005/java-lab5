package com.sergey;

/**
 * Первая реализация интерфейса {@link SomeInterface}
 * @author Белявцев Сергей
 * @version 1.0
 */
public class SomeImpl implements SomeInterface {
    @Override
    public void doSomething() {
        System.out.print("A");
    }
}