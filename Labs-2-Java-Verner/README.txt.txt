# Лабораторная работа 2: Система управления персонами и пропусками

## Описание проекта
Программа для управления студентами и парковочными пропусками с возможностью сохранения данных в файлы.

## Функциональность

### Основные требования:
- ✅ Класс Person с полями: имя, фамилия, возраст, телефон
- ✅ Класс Student (наследник Person) с полями: группа, курс
- ✅ Класс-утилита PersonPrinter для вывода информации
- ✅ Интерфейс Dismissable для отчисления студентов
- ✅ Демонстрация работы всех классов

### Дополнительный функционал:
- 🚗 Система парковочных пропусков
- 💾 Сохранение данных в файлы
- 🎨 Цветной интерфейс в терминале
- 📊 Интерактивное меню управления

## Структура проекта
src/
└── ru/
└── bmstu/
├── Main.java
├── domain/
│ ├── Person.java
│ ├── Student.java
│ └── parking/
│ ├── ParkingPass.java
│ └── Vehicle.java
├── service/
│ ├── PersonPrinter.java
│ ├── PersonDemo.java
│ ├── Dismissable.java
│ ├── StudentDismisser.java
│ ├── StudentManager.java
│ └── parking/
│ ├── ParkingPassService.java
│ └── FileDataManager.java
└── utils/
└── ConsoleColors.java
