package com.sergey;

/**
 * Единственная реализация интерфейса {@link SomeOtherInterface}
 * "SO" в названии - это сокращение от "SomeOther"
 *
 * @author Белявцев Сергей
 * @version 1.0
 */
public class SODoer implements SomeOtherInterface {
    @Override
    public void doSomething() {
        System.out.print("C");
    }
}