package ru.bmstu.service;

import ru.bmstu.domain.Student;
import ru.bmstu.domain.parking.ParkingPass;
import ru.bmstu.service.parking.ParkingPassService;
import ru.bmstu.utils.ConsoleColors;

import java.util.*;

public class StudentManager {
    private List<Student> students;
    private ParkingPassService parkingService;
    private Scanner scanner;

    public StudentManager(ParkingPassService parkingService) {
        this.students = new ArrayList<>();
        this.parkingService = parkingService;
        this.scanner = new Scanner(System.in);
    }

    public void addStudent(Student student, boolean needPass) {
        students.add(student);
        System.out.println(ConsoleColors.GREEN + "✅ Студент добавлен!" + ConsoleColors.RESET);

        if (needPass) {
            System.out.println(ConsoleColors.CYAN + "🚗 Запуск процесса оформления пропуска..." + ConsoleColors.RESET);
            createParkingPassForStudent(student);
        }
    }

    public void updateStudent(int index) {
        if (index < 0 || index >= students.size()) {
            System.out.println(ConsoleColors.RED + "❌ Неверный индекс студента!" + ConsoleColors.RESET);
            return;
        }

        Student student = students.get(index);
        System.out.println(ConsoleColors.YELLOW + "✏️  Редактирование студента: " + student.getFirstName() + " " + student.getSecondName() + ConsoleColors.RESET);

        System.out.print("Введите новое имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите новую фамилию: ");
        String secondName = scanner.nextLine();
        System.out.print("Введите новый возраст: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новый телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите новую группу: ");
        String group = scanner.nextLine();
        System.out.print("Введите новый курс: ");
        int course = Integer.parseInt(scanner.nextLine());

        // Обновляем данные через сеттеры
        student.setAge(age);
        student.setPhone(phone);
        student.setGroup(group);
        student.setCourse(course);

        System.out.println(ConsoleColors.GREEN + "✅ Данные студента обновлены!" + ConsoleColors.RESET);
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "📭 Список студентов пуст!" + ConsoleColors.RESET);
            return;
        }

        // Сортировка от старших курсов к младшим
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Integer.compare(s2.getCourse(), s1.getCourse()));

        System.out.println(ConsoleColors.CYAN_BOLD + "\n🎓 СПИСОК СТУДЕНТОВ (от старших курсов к младшим):" + ConsoleColors.RESET);
        System.out.println("─".repeat(80));

        for (int i = 0; i < sortedStudents.size(); i++) {
            Student student = sortedStudents.get(i);
            System.out.printf(ConsoleColors.YELLOW + "%d. " + ConsoleColors.RESET, i + 1);
            System.out.printf("👤 %s %s | 🎯 Курс: %d | 🏷️  Группа: %s | 📞 %s\n",
                    student.getFirstName(), student.getSecondName(),
                    student.getCourse(), student.getGroup(),
                    student.getPhone() != null ? student.getPhone() : "нет телефона");
        }
        System.out.println("─".repeat(80));
    }

    public void issueParkingPass() {
        if (students.isEmpty()) {
            System.out.println(ConsoleColors.RED + "❌ Нет студентов для выдачи пропуска!" + ConsoleColors.RESET);
            return;
        }

        displayAllStudents();
        System.out.print(ConsoleColors.CYAN + "Введите номер студента для выдачи пропуска: " + ConsoleColors.RESET);
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        if (choice >= 0 && choice < students.size()) {
            Student student = students.get(choice);
            createParkingPassForStudent(student);
        } else {
            System.out.println(ConsoleColors.RED + "❌ Неверный выбор!" + ConsoleColors.RESET);
        }
    }

    public void revokeParkingPass() {
        if (students.isEmpty()) {
            System.out.println(ConsoleColors.RED + "❌ Нет студентов!" + ConsoleColors.RESET);
            return;
        }

        // Находим студентов с пропусками
        List<Student> studentsWithPasses = new ArrayList<>();
        List<String> passNumbers = new ArrayList<>();

        for (Student student : students) {
            for (ParkingPass pass : parkingService.getAllPasses()) {
                if (pass.getOwner().equals(student)) {
                    studentsWithPasses.add(student);
                    passNumbers.add(pass.getPassNumber());
                }
            }
        }

        if (studentsWithPasses.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "📭 Нет студентов с активными пропусками!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "\n🚗 СТУДЕНТЫ С ПРОПУСКАМИ:" + ConsoleColors.RESET);
        for (int i = 0; i < studentsWithPasses.size(); i++) {
            Student student = studentsWithPasses.get(i);
            System.out.printf(ConsoleColors.YELLOW + "%d. " + ConsoleColors.RESET +
                            "%s %s | 🏷️  Пропуск: %s\n", i + 1,
                    student.getFirstName(), student.getSecondName(), passNumbers.get(i));
        }

        System.out.print(ConsoleColors.CYAN + "Введите номер студента для изъятия пропуска: " + ConsoleColors.RESET);
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        if (choice >= 0 && choice < studentsWithPasses.size()) {
            String passNumber = passNumbers.get(choice);
            parkingService.deactivatePass(passNumber);
            System.out.println(ConsoleColors.GREEN + "✅ Пропуск " + passNumber + " отозван!" + ConsoleColors.RESET);
        }
    }

    private void createParkingPassForStudent(Student student) {
        System.out.println(ConsoleColors.CYAN + "\n🚗 ОФОРМЛЕНИЕ ПАРКОВОЧНОГО ПРОПУСКА" + ConsoleColors.RESET);
        System.out.print("Введите марку автомобиля: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля: ");
        String model = scanner.nextLine();
        System.out.print("Введите гос. номер: ");
        String licensePlate = scanner.nextLine();

        String passNumber = "P" + String.format("%03d", parkingService.getAllPasses().size() + 1);
        parkingService.createParkingPass(passNumber, student, brand, model, licensePlate);

        System.out.println(ConsoleColors.GREEN + "✅ Пропуск " + passNumber + " оформлен для " +
                student.getFirstName() + " " + student.getSecondName() + ConsoleColors.RESET);
    }

    public List<Student> getStudents() {
        return students;
    }
}