package ru.bmstu.service;

import ru.bmstu.domain.Person;

public class PersonDemo {
    private Person person;

    // Конструктор, инициализирующий поле (требование 7)
    public PersonDemo(Person person) {
        this.person = person;
    }

    // Метод demo(), который демонстрирует работу класса-утилиты PersonPrinter (требование 7)
    public void demo() {
        System.out.println("=== Демонстрация PersonPrinter ===");
        PersonPrinter.printFI(person);
        PersonPrinter.printInfo(person);
    }

    // Метод, демонстрирующий работу написанных классов (требование 11)
    public void demonstrateDismissable() {
        System.out.println("=== Демонстрация Dismissable ===");
        StudentDismisser dismisser = new StudentDismisser();
        dismisser.dismiss(person);
    }
}