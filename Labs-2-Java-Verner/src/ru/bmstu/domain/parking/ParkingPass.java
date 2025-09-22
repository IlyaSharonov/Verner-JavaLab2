package ru.bmstu.domain.parking;

import ru.bmstu.domain.Person;

public class ParkingPass {
    private String passNumber;
    private Person owner;
    private Vehicle vehicle;
    private boolean isActive;

    public ParkingPass(String passNumber, Person owner, Vehicle vehicle) {
        this.passNumber = passNumber;
        this.owner = owner;
        this.vehicle = vehicle;
        this.isActive = true;
    }

    // Геттеры
    public String getPassNumber() { return passNumber; }
    public Person getOwner() { return owner; }
    public Vehicle getVehicle() { return vehicle; }
    public boolean isActive() { return isActive; }

    // Сеттеры
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "Пропуск №" + passNumber +
                "\nВладелец: " + owner.getFirstName() + " " + owner.getSecondName() +
                "\nАвтомобиль: " + vehicle.toString() +
                "\nСтатус: " + (isActive ? "активен" : "неактивен");
    }

    // Метод для экспорта в строку (для записи в файл)
    public String toCsvString() {
        return passNumber + "," +
                owner.getFirstName() + "," +
                owner.getSecondName() + "," +
                owner.getAge() + "," +
                (owner.getPhone() != null ? owner.getPhone() : "null") + "," +
                vehicle.getBrand() + "," +
                vehicle.getModel() + "," +
                vehicle.getLicensePlate() + "," +
                isActive;
    }
}