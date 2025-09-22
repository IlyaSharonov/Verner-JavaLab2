package ru.bmstu.service;

import ru.bmstu.domain.Person;

public interface Dismissable {
    default void dismiss(Person p) {
        System.out.println("Отчислен: " + p.getFirstName() + " " + p.getSecondName());
    }
}