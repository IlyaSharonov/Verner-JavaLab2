package ru.bmstu.service.parking;

import ru.bmstu.domain.Person;
import ru.bmstu.domain.parking.ParkingPass;
import ru.bmstu.domain.parking.Vehicle;
import ru.bmstu.domain.Student;
import ru.bmstu.service.StudentManager;
import ru.bmstu.utils.ConsoleColors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDataManager {
    private static final String FILE_NAME = "parking_passes.csv";
    private static final String BACKUP_FOLDER = "backups/";

    // Существующие методы для работы с пропусками
    public void exportToFile(ParkingPassService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("passNumber,firstName,secondName,age,phone,brand,model,licensePlate,isActive");

            for (ParkingPass pass : service.getAllPasses()) {
                writer.println(pass.toCsvString());
            }
            System.out.println(ConsoleColors.GREEN + "✅ Данные успешно экспортированы в файл: " + FILE_NAME + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "❌ Ошибка при экспорте данных: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public List<ParkingPass> importFromFile() {
        List<ParkingPass> importedPasses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine(); // Пропускаем заголовок

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 9) {
                    Person person = new Person(data[1], data[2], Integer.parseInt(data[3]),
                            "null".equals(data[4]) ? null : data[4]);

                    Vehicle vehicle = new Vehicle(data[5], data[6], data[7]);
                    ParkingPass pass = new ParkingPass(data[0], person, vehicle);
                    pass.setActive(Boolean.parseBoolean(data[8]));

                    importedPasses.add(pass);
                }
            }
            System.out.println(ConsoleColors.GREEN + "✅ Импортировано " + importedPasses.size() + " пропусков из файла" + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "❌ Ошибка при импорте данных: " + e.getMessage() + ConsoleColors.RESET);
        }

        return importedPasses;
    }

    // НОВЫЕ МЕТОДЫ ДЛЯ РАБОТЫ С BACKUP ФАЙЛАМИ

    public void saveAllData(StudentManager studentManager, ParkingPassService parkingService) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String fileName = BACKUP_FOLDER + timestamp + ".txt";

        new File(BACKUP_FOLDER).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Сохраняем студентов
            writer.println("=== STUDENTS ===");
            for (Student student : studentManager.getStudents()) {
                writer.printf("%s,%s,%d,%s,%s,%d%n",
                        student.getFirstName(), student.getSecondName(),
                        student.getAge(),
                        student.getPhone() != null ? student.getPhone() : "null",
                        student.getGroup(), student.getCourse());
            }

            // Сохраняем пропуска
            writer.println("=== PARKING_PASSES ===");
            for (ParkingPass pass : parkingService.getAllPasses()) {
                writer.println(pass.toCsvString());
            }

            System.out.println(ConsoleColors.GREEN + "✅ Все данные сохранены в файл: " + fileName + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "❌ Ошибка при сохранении: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public boolean importAllDataFromFile(String fileName, StudentManager studentManager, ParkingPassService parkingService) {
        File file = new File(BACKUP_FOLDER + fileName + ".txt");
        if (!file.exists()) {
            System.out.println(ConsoleColors.RED + "❌ Файл не найден: " + file.getPath() + ConsoleColors.RESET);
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean readingStudents = false;
            boolean readingPasses = false;
            int studentsImported = 0;
            int passesImported = 0;

            // Очищаем текущие данные
            studentManager.getStudents().clear();
            // Для parkingService нужно создать метод clear() или работать с новым экземпляром

            while ((line = reader.readLine()) != null) {
                if (line.equals("=== STUDENTS ===")) {
                    readingStudents = true;
                    readingPasses = false;
                    continue;
                } else if (line.equals("=== PARKING_PASSES ===")) {
                    readingStudents = false;
                    readingPasses = true;
                    continue;
                } else if (line.isEmpty()) {
                    continue;
                }

                if (readingStudents) {
                    // Импорт студента
                    String[] studentData = line.split(",");
                    if (studentData.length == 6) {
                        Student student = new Student(
                                studentData[0], studentData[1],
                                Integer.parseInt(studentData[2]),
                                "null".equals(studentData[3]) ? null : studentData[3],
                                studentData[4], Integer.parseInt(studentData[5])
                        );
                        studentManager.addStudent(student, false);
                        studentsImported++;
                    }
                } else if (readingPasses) {
                    // Импорт пропуска
                    String[] passData = line.split(",");
                    if (passData.length == 9) {
                        // Создаем Person для пропуска
                        Person person = new Person(
                                passData[1], passData[2],
                                Integer.parseInt(passData[3]),
                                "null".equals(passData[4]) ? null : passData[4]
                        );

                        Vehicle vehicle = new Vehicle(passData[5], passData[6], passData[7]);
                        ParkingPass pass = new ParkingPass(passData[0], person, vehicle);
                        pass.setActive(Boolean.parseBoolean(passData[8]));

                        parkingService.addPass(pass);
                        passesImported++;
                    }
                }
            }

            System.out.println(ConsoleColors.GREEN + "✅ Импорт завершен! Студентов: " + studentsImported +
                    ", Пропусков: " + passesImported + ConsoleColors.RESET);
            return true;

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "❌ Ошибка при импорте: " + e.getMessage() + ConsoleColors.RESET);
            return false;
        }
    }

    public void displayBackupFiles() {
        File backupDir = new File(BACKUP_FOLDER);
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            System.out.println(ConsoleColors.YELLOW + "📭 Папка backups не существует или пуста!" + ConsoleColors.RESET);
            return;
        }

        File[] files = backupDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println(ConsoleColors.YELLOW + "📭 Нет backup файлов!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "\n📂 ДОСТУПНЫЕ BACKUP ФАЙЛЫ:" + ConsoleColors.RESET);
        for (int i = 0; i < files.length; i++) {
            System.out.printf(ConsoleColors.YELLOW + "%d. " + ConsoleColors.RESET + "%s (%.1f KB)%n",
                    i + 1, files[i].getName(), files[i].length() / 1024.0);
        }
    }

    public String getLastBackupFileName() {
        File backupDir = new File(BACKUP_FOLDER);
        if (!backupDir.exists()) return null;

        File[] files = backupDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) return null;

        File lastFile = files[0];
        for (File file : files) {
            if (file.lastModified() > lastFile.lastModified()) {
                lastFile = file;
            }
        }
        return lastFile.getName().replace(".txt", "");
    }

    public void displayFileContent() {
        System.out.println(ConsoleColors.CYAN + "=== СОДЕРЖИМОЕ ФАЙЛА " + FILE_NAME + " ===" + ConsoleColors.RESET);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "❌ Файл не найден или ошибка чтения: " + e.getMessage() + ConsoleColors.RESET);
        }
        System.out.println(ConsoleColors.CYAN + "=================================" + ConsoleColors.RESET);
    }
}