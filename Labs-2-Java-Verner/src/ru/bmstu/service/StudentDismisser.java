package ru.bmstu.service;

import ru.bmstu.domain.Person;
import ru.bmstu.domain.Student;

public class StudentDismisser implements Dismissable {

    @Override
    public void dismiss(Person p) {
        if (p instanceof Student) {
            Student student = (Student) p;
            System.out.println("=== Информация о студенте для отчисления ===");
            System.out.println("Имя: " + student.getFirstName());
            System.out.println("Фамилия: " + student.getSecondName());
            System.out.println("Возраст: " + student.getAge());
            System.out.println("Телефон: " + (student.getPhone() != null ? student.getPhone() : "не указан"));
            System.out.println("Группа: " + student.getGroup());
            System.out.println("Курс: " + student.getCourse());
        } else {
            System.out.println("Ошибка: " + p.getFirstName() + " " + p.getSecondName() + " не является студентом!");
        }
    }
}