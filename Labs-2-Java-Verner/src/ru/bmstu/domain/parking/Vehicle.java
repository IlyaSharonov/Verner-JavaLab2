package ru.bmstu.domain.parking;

public class Vehicle {
    private String brand;
    private String model;
    private String licensePlate;

    public Vehicle(String brand, String model, String licensePlate) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    // Геттеры
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getLicensePlate() { return licensePlate; }

    // Сеттеры
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    @Override
    public String toString() {
        return brand + " " + model + " (" + licensePlate + ")";
    }
}