package com.sergey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки полей, в которые {@link Injector} должен
 * внедрить зависимость
 *
 * @author Белявцев Сергей
 * @version 1.0
 * @see Injector
 */
@Retention(RetentionPolicy.RUNTIME) // Аннотация должна быть доступна во время выполнения
@Target(ElementType.FIELD)          // Аннотация может применяться только к полям
public @interface AutoInjectable {
}