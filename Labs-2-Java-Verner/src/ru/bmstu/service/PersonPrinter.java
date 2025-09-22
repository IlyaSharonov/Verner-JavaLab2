package ru.bmstu.service;

import ru.bmstu.domain.Person;

public class PersonPrinter {

    // Вывод имени и фамилии
    public static void printFI(Person p) {
        System.out.println("Имя: " + p.getFirstName() + ", Фамилия: " + p.getSecondName());
    }

    // Вывод полной информации о человеке
    public static void printInfo(Person p) {
        System.out.println("=== Полная информация ===");
        System.out.println("Имя: " + p.getFirstName());
        System.out.println("Фамилия: " + p.getSecondName());
        System.out.println("Возраст: " + p.getAge());
        System.out.println("Телефон: " + (p.getPhone() != null ? p.getPhone() : "не указан"));

        // Если это Student, выводим дополнительную информацию
        if (p instanceof ru.bmstu.domain.Student) {
            ru.bmstu.domain.Student student = (ru.bmstu.domain.Student) p;
            System.out.println("Группа: " + student.getGroup());
            System.out.println("Курс: " + student.getCourse());
        }
        System.out.println("========================");
    }
}