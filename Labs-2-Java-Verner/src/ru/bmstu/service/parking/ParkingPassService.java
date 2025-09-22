package ru.bmstu.service.parking;

import ru.bmstu.domain.Person;
import ru.bmstu.domain.parking.ParkingPass;
import ru.bmstu.domain.parking.Vehicle;
import ru.bmstu.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class ParkingPassService {
    private List<ParkingPass> parkingPasses;

    public ParkingPassService() {
        this.parkingPasses = new ArrayList<>();
    }

    // Создание нового пропуска
    public ParkingPass createParkingPass(String passNumber, Person owner,
                                         String brand, String model, String licensePlate) {
        Vehicle vehicle = new Vehicle(brand, model, licensePlate);
        ParkingPass pass = new ParkingPass(passNumber, owner, vehicle);
        parkingPasses.add(pass);
        return pass;
    }

    // Поиск пропуска по номеру
    public ParkingPass findPassByNumber(String passNumber) {
        return parkingPasses.stream()
                .filter(pass -> pass.getPassNumber().equals(passNumber))
                .findFirst()
                .orElse(null);
    }

    // Получение всех пропусков
    public List<ParkingPass> getAllPasses() {
        return new ArrayList<>(parkingPasses);
    }

    // Деактивация пропуска
    public boolean deactivatePass(String passNumber) {
        ParkingPass pass = findPassByNumber(passNumber);
        if (pass != null) {
            pass.setActive(false);
            return true;
        }
        return false;
    }

    // Добавление существующего пропуска (для импорта из файла)
    public void addPass(ParkingPass pass) {
        parkingPasses.add(pass);
    }
    public void clearPasses() {
        parkingPasses.clear();
        System.out.println(ConsoleColors.YELLOW + "🗑️  Все пропуска очищены!" + ConsoleColors.RESET);
    }
}