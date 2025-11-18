package com.sergey;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    public void foo() {
        if (field1 == null || field2 == null) {
            System.out.println("Поля не инициализированы! Вы забыли использовать Injector?");
            return;
        }
        field1.doSomething();
        field2.doSomething();
    }
}