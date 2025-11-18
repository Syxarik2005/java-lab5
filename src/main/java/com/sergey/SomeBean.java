package com.sergey;

/**
 * Пример класса ("бина"), который содержит зависимости,
 * нуждающиеся во внедрении
 *
 * @author Белявцев Сергей
 * @version 1.0
 * @see AutoInjectable
 * @see Injector
 */
public class SomeBean {
    /**
     * Поле, которое будет инициализировано инжектором
     */
    @AutoInjectable
    private SomeInterface field1;

    /**
     * Второе поле, которое будет инициализировано инжектором
     */
    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Вызывает методы на внедренных зависимостях.
     * Если зависимости не были внедрены, выведет сообщение об ошибке
     */
    public void foo() {
        if (field1 == null || field2 == null) {
            System.out.println("Поля не инициализированы! Вы забыли использовать Injector?");
            return;
        }
        field1.doSomething();
        field2.doSomething();
    }
}