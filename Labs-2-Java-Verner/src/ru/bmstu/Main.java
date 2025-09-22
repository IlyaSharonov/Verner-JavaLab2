package ru.bmstu;

import ru.bmstu.domain.Person;
import ru.bmstu.domain.Student;
import ru.bmstu.service.PersonDemo;
import ru.bmstu.service.parking.FileDataManager;
import ru.bmstu.service.parking.ParkingPassService;
import ru.bmstu.service.StudentManager;
import ru.bmstu.utils.ConsoleColors;

import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ДЕМОНСТРАЦИЯ ПРОГРАММЫ                    ║");
        System.out.println("║          Система управления персонами и пропусками          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // =========================================================================
        // ЧАСТЬ 1: ВЫПОЛНЕНИЕ ОСНОВНЫХ ТРЕБОВАНИЙ ЗАДАНИЯ
        // =========================================================================

        System.out.println("▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("ЧАСТЬ 1: ОСНОВНЫЕ ТРЕБОВАНИЯ ЗАДАНИЯ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");

        // Создание объектов Person (требования 1-4)
        System.out.println("📋 1. СОЗДАНИЕ ОБЪЕКТОВ PERSON:");
        System.out.println("────────────────────────────────");

        Person person1 = new Person("Иван", "Медведев", 19);
        System.out.println("✅ Создан Person: " + person1.getFirstName() + " " + person1.getSecondName() +
                ", возраст: " + person1.getAge() + " (без телефона)");

        Person person2 = new Person("Илья", "Вернер", 19, "+7-919-077-43-00");
        System.out.println("✅ Создан Person: " + person2.getFirstName() + " " + person2.getSecondName() +
                ", возраст: " + person2.getAge() + ", телефон: " + person2.getPhone());

        // Создание объекта Student (требование 8)
        System.out.println("\n🎓 2. СОЗДАНИЕ ОБЪЕКТА STUDENT:");
        System.out.println("────────────────────────────────");

        Student student = new Student("Анна", "Сидорова", 20, "+7-888-765-43-21", "ИУК5-51", 3);
        System.out.println("✅ Создан Student: " + student.getFirstName() + " " + student.getSecondName() +
                ", группа: " + student.getGroup() + ", курс: " + student.getCourse());

        // Демонстрация работы PersonPrinter (требования 5-7)
        System.out.println("\n🖨️  3. ДЕМОНСТРАЦИЯ PERSONPRINTER:");
        System.out.println("────────────────────────────────");

        System.out.println("--- Демонстрация для Person без телефона ---");
        PersonDemo demo1 = new PersonDemo(person1);
        demo1.demo();

        System.out.println("\n--- Демонстрация для Person с телефоном ---");
        PersonDemo demo2 = new PersonDemo(person2);
        demo2.demo();

        System.out.println("\n--- Демонстрация для Student ---");
        PersonDemo studentDemo = new PersonDemo(student);
        studentDemo.demo();

        // Демонстрация работы Dismissable (требования 9-11)
        System.out.println("\n🚫 4. ДЕМОНСТРАЦИЯ ИНТЕРФЕЙСА DISMISSABLE:");
        System.out.println("────────────────────────────────");

        System.out.println("--- Отчисление студента ---");
        studentDemo.demonstrateDismissable();

        System.out.println("\n--- Попытка отчисления обычного Person ---");
        PersonDemo nonStudentDemo = new PersonDemo(person1);
        nonStudentDemo.demonstrateDismissable();

        // =========================================================================
        // ЧАСТЬ 2: ДОПОЛНИТЕЛЬНЫЙ ФУНКЦИОНАЛ (ПАРКОВОЧНЫЕ ПРОПУСКИ)
        // =========================================================================

        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("ЧАСТЬ 2: ДОПОЛНИТЕЛЬНЫЙ ФУНКЦИОНАЛ - СИСТЕМА ПАРКОВКИ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");

        ParkingPassService parkingService = new ParkingPassService();
        FileDataManager fileManager = new FileDataManager();

        // Создание парковочных пропусков
        System.out.println("🚗 5. СОЗДАНИЕ ПАРКОВОЧНЫХ ПРОПУСКОВ:");
        System.out.println("────────────────────────────────");

        var pass1 = parkingService.createParkingPass("P001", person1, "Toyota", "Camry", "А123ВС77");
        System.out.println("✅ Создан пропуск P001 для: " + person1.getFirstName() + " " + person1.getSecondName());

        var pass2 = parkingService.createParkingPass("P002", person2, "BMW", "X5", "У456НК77");
        System.out.println("✅ Создан пропуск P002 для: " + person2.getFirstName() + " " + person2.getSecondName());

        var pass3 = parkingService.createParkingPass("P003", student, "Lada", "Vesta", "О789МР77");
        System.out.println("✅ Создан пропуск P003 для: " + student.getFirstName() + " " + student.getSecondName());

        // Показ информации о пропусках
        System.out.println("\n📄 6. ИНФОРМАЦИЯ О СОЗДАННЫХ ПРОПУСКАХ:");
        System.out.println("────────────────────────────────");

        System.out.println("--- Пропуск P001 ---");
        System.out.println(pass1);

        System.out.println("\n--- Пропуск P002 ---");
        System.out.println(pass2);

        System.out.println("\n--- Пропуск P003 ---");
        System.out.println(pass3);

        // Экспорт данных в файл
        System.out.println("\n💾 7. ЭКСПОРТ ДАННЫХ В ФАЙЛ:");
        System.out.println("────────────────────────────────");
        fileManager.exportToFile(parkingService);

        // Показ содержимого файла
        System.out.println("\n📁 8. СОДЕРЖИМОЕ ФАЙЛА:");
        System.out.println("────────────────────────────────");
        fileManager.displayFileContent();

        // Деактивация пропуска
        System.out.println("\n⚡ 9. ДЕАКТИВАЦИЯ ПРОПУСКА P002:");
        System.out.println("────────────────────────────────");
        boolean deactivated = parkingService.deactivatePass("P002");
        if (deactivated) {
            System.out.println("✅ Пропуск P002 успешно деактивирован");
        }

        // Повторный экспорт после изменений
        System.out.println("\n💾 10. ПОВТОРНЫЙ ЭКСПОРТ ПОСЛЕ ИЗМЕНЕНИЙ:");
        System.out.println("────────────────────────────────");
        fileManager.exportToFile(parkingService);

        // Импорт данных из файла
        System.out.println("\n📥 11. ИМПОРТ ДАННЫХ ИЗ ФАЙЛА:");
        System.out.println("────────────────────────────────");
        ParkingPassService importedService = new ParkingPassService();
        var importedPasses = fileManager.importFromFile();

        for (var pass : importedPasses) {
            importedService.addPass(pass);
        }

        // Показ импортированных данных
        System.out.println("\n🔍 12. ПРОВЕРКА ИМПОРТИРОВАННЫХ ДАННЫХ:");
        System.out.println("────────────────────────────────");
        System.out.println("Импортировано пропусков: " + importedService.getAllPasses().size());

        for (var pass : importedService.getAllPasses()) {
            System.out.println("\n" + pass);
            System.out.println("─".repeat(50));
        }

        // =========================================================================
        // ЗАКЛЮЧЕНИЕ
        // =========================================================================

        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("ИТОГИ ВЫПОЛНЕНИЯ:");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");

        System.out.println("✅ ВСЕ ОСНОВНЫЕ ТРЕБОВАНИЯ ВЫПОЛНЕНЫ:");
        System.out.println("   ✓ 1-4: Класс Person создан с правильными полями и методами");
        System.out.println("   ✓ 5-6: PersonPrinter с методами printFI() и printInfo()");
        System.out.println("   ✓ 7: PersonDemo с конструктором и методом demo()");
        System.out.println("   ✓ 8: Student наследует Person с дополнительными полями");
        System.out.println("   ✓ 9-10: Интерфейс Dismissable и класс StudentDismisser");
        System.out.println("   ✓ 11: Метод demonstrateDismissable() в PersonDemo");
        System.out.println("   ✓ 12: Демонстрация запущена в Main");

        System.out.println("\n🎁 ДОПОЛНИТЕЛЬНЫЙ ФУНКЦИОНАЛ:");
        System.out.println("   ✓ Система парковочных пропусков");
        System.out.println("   ✓ Работа с файлами (экспорт/импорт)");
        System.out.println("   ✓ Управление состоянием пропусков");

        // =========================================================================
// ЧАСТЬ 3: ИНТЕРАКТИВНОЕ МЕНЮ УПРАВЛЕНИЯ
// =========================================================================

        System.out.println("\n▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        System.out.println("ЧАСТЬ 3: ИНТЕРАКТИВНАЯ СИСТЕМА УПРАВЛЕНИЯ");
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");

        StudentManager studentManager = new StudentManager(parkingService);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

// Добавляем существующих студентов для демонстрации
        studentManager.addStudent(student, false);

        while (running) {
            System.out.println(ConsoleColors.PURPLE_BOLD + "\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                 МЕНЮ УПРАВЛЕНИЯ СИСТЕМОЙ                 ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "1. Добавить студента" + ConsoleColors.PURPLE_BOLD + "                                      ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "2. Изменить студента" + ConsoleColors.PURPLE_BOLD + "                                      ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "3. Вывести всех студентов" + ConsoleColors.PURPLE_BOLD + "                                 ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "4. Выдать пропуск" + ConsoleColors.PURPLE_BOLD + "                                         ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "5. Отобрать пропуск" + ConsoleColors.PURPLE_BOLD + "                                       ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "6. Сохранить данные в файл" + ConsoleColors.PURPLE_BOLD + "                                ║");
            System.out.println("║ " + ConsoleColors.CYAN_BOLD + "7. Экспорт из файла" + ConsoleColors.PURPLE_BOLD + "                                       ║");
            System.out.println("║ " + ConsoleColors.RED_BOLD + "0. Выход" + ConsoleColors.PURPLE_BOLD + "                                                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝" + ConsoleColors.RESET);

            System.out.print(ConsoleColors.YELLOW + "Выберите опцию (0-7): " + ConsoleColors.RESET);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println(ConsoleColors.GREEN_BOLD + "\n🎓 ДОБАВЛЕНИЕ НОВОГО СТУДЕНТА" + ConsoleColors.RESET);
                    System.out.print("Введите имя: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Введите фамилию: ");
                    String secondName = scanner.nextLine();
                    System.out.print("Введите возраст: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Введите телефон: ");
                    String phone = scanner.nextLine();
                    System.out.print("Введите группу: ");
                    String group = scanner.nextLine();
                    System.out.print("Введите курс: ");
                    int course = Integer.parseInt(scanner.nextLine());
                    System.out.print("Нужен парковочный пропуск? (да/нет): ");
                    boolean needPass = scanner.nextLine().equalsIgnoreCase("да");

                    Student newStudent = new Student(firstName, secondName, age, phone, group, course);
                    studentManager.addStudent(newStudent, needPass);
                    break;

                case 2:
                    System.out.println(ConsoleColors.YELLOW_BOLD + "\n✏️  ИЗМЕНЕНИЕ ДАННЫХ СТУДЕНТА" + ConsoleColors.RESET);
                    studentManager.displayAllStudents();
                    if (!studentManager.getStudents().isEmpty()) {
                        System.out.print("Введите номер студента для изменения: ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        studentManager.updateStudent(index);
                    }
                    break;

                case 3:
                    studentManager.displayAllStudents();
                    break;

                case 4:
                    System.out.println(ConsoleColors.BLUE_BOLD + "\n🚗 ВЫДАЧА ПАРКОВОЧНОГО ПРОПУСКА" + ConsoleColors.RESET);
                    studentManager.issueParkingPass();
                    break;

                case 5:
                    System.out.println(ConsoleColors.RED_BOLD + "\n🚫 ОТЗЫВ ПАРКОВОЧНОГО ПРОПУСКА" + ConsoleColors.RESET);
                    studentManager.revokeParkingPass();
                    break;

                case 6:
                    System.out.println(ConsoleColors.CYAN_BOLD + "\n💾 СОХРАНЕНИЕ ДАННЫХ В ФАЙЛ" + ConsoleColors.RESET);
                    fileManager.saveAllData(studentManager, parkingService);
                    break;
                case 7:
                    System.out.println(ConsoleColors.GREEN_BOLD + "\n📥 ЭКСПОРТ ДАННЫХ ИЗ ФАЙЛА" + ConsoleColors.RESET);
                    fileManager.displayBackupFiles();

                    if (new File("backups/").exists() && new File("backups/").listFiles().length > 0) {
                        System.out.print("Введите 'last' для последнего файла, номер файла или имя: ");
                        String fileChoice = scanner.nextLine();

                        String selectedFileName = null;

                        if (fileChoice.equalsIgnoreCase("last")) {
                            selectedFileName = fileManager.getLastBackupFileName();
                            if (selectedFileName != null) {
                                System.out.println(ConsoleColors.YELLOW + "📁 Загрузка последнего файла: " + selectedFileName + ConsoleColors.RESET);
                            }
                        } else {
                            try {
                                int fileIndex = Integer.parseInt(fileChoice) - 1;
                                File backupDir = new File("backups/");
                                File[] files = backupDir.listFiles((dir, name) -> name.endsWith(".txt"));
                                if (fileIndex >= 0 && fileIndex < files.length) {
                                    selectedFileName = files[fileIndex].getName().replace(".txt", "");
                                }
                            } catch (NumberFormatException e) {
                                selectedFileName = fileChoice;
                            }
                        }

                        if (selectedFileName != null) {
                            System.out.print("Очистить текущие данные перед импортом? (да/нет): ");
                            boolean clearData = scanner.nextLine().equalsIgnoreCase("да");

                            if (clearData) {
                                studentManager.getStudents().clear();
                                parkingService.clearPasses();
                            }

                            boolean success = fileManager.importAllDataFromFile(selectedFileName, studentManager, parkingService);
                            if (success) {
                                System.out.println(ConsoleColors.GREEN + "✅ Данные успешно загружены в программу!" + ConsoleColors.RESET);
                                System.out.println(ConsoleColors.CYAN + "👥 Студентов: " + studentManager.getStudents().size() +
                                        " | 🚗 Пропусков: " + parkingService.getAllPasses().size() + ConsoleColors.RESET);
                            }
                        } else {
                            System.out.println(ConsoleColors.RED + "❌ Файл не найден!" + ConsoleColors.RESET);
                        }
                    } else {
                        System.out.println(ConsoleColors.YELLOW + "📭 Нет backup файлов для импорта!" + ConsoleColors.RESET);
                    }
                    break;

                case 0:
                    System.out.println(ConsoleColors.RED_BOLD + "\n👋 Завершение работы программы..." + ConsoleColors.RESET);
                    running = false;
                    break;

                default:
                    System.out.println(ConsoleColors.RED + "❌ Неверный выбор! Попробуйте снова." + ConsoleColors.RESET);
            }
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║             ПРОГРАММА ЗАВЕРШИЛА РАБОТУ                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

}