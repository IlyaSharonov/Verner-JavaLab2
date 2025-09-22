package ru.bmstu.domain;

public class Person {
    private String firstName;
    private String secondName;
    private int age;
    private String phone;

    // Конструктор с параметрами: имя, фамилия, возраст
    public Person(String firstName, String secondName, int age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }

    // Конструктор с параметрами: имя, фамилия, возраст, телефон
    public Person(String firstName, String secondName, int age, String phone) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.phone = phone;
    }

    // Геттеры для всех полей
    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    // Сеттеры для полей: age и phone
    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}