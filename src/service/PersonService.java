package service;

import model.Person;
import repository.PersonRepositoryImpl;
import util.Text;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PersonService {

    PersonRepositoryImpl personRepository = new PersonRepositoryImpl();
    Scanner scanner = new Scanner(System.in);
    Person person;

    public void enter() {

        String login;
        String password;
        Text.welcome();

        for (int i = 0; i < 3; i++) {
            System.out.print("Введите логин : ");
            login = scanner.nextLine().trim();
            System.out.print("Введите пароль : ");
            password = scanner.nextLine().trim();
            Optional<Person> OptionalPerson = personRepository.enter(login, password);
            if (OptionalPerson.isEmpty()) {
                System.out.println("Неверный логин или пароль");
                continue;
            }
            System.out.println("Добро пожаловать");
            person = OptionalPerson.get();
            switch (person.getStatus()) {
                case "USER" -> userPath(person);
                case "ADMIN" -> adminPath(person);
            }


        }

    }

    public void adminPath(Person person) {
        System.out.println("Путь админа");
        int attempts = 0;
        while (true) {
            System.out.println("Что хотите сделать?");
            System.out.println("""
                    1.Создать ивент
                    2.Удалить ивент
                    3.Редактировать ивент
                    4.Посмотреть на какие мероприятия записан
                    5.Записаться на мероприятие
                    6.Сняться с мероприятия
                    7.Посмотреть все мероприятия
                    8.Удалить пользователя
                    9.Удалить базу
                    0.Выйти
                    """);
            System.out.print("Ввод : ");
            switch (scanner.nextLine()) {

                case "1" -> EventServiceImpl.createEvent(); // ГОТОВО
                case "2" -> EventServiceImpl.delete(); //ГОТОВО
                case "3" -> EventServiceImpl.redactEvent();
                case "4" -> EventServiceImpl.checkWhatEventsRecorded(person); //ГОТОВО
                case "5" -> EventServiceImpl.signUpForAnEvent(person);
                case "6" -> EventServiceImpl.withdrawFromTheEvent(person);
                case "7" -> EventServiceImpl.watchAllEvent();
                case "8" -> deletePerson();
                case "0" -> System.exit(0);

                default -> {
                    attempts++;
                    System.out.println("Неправильный ввод");
                    if (attempts > 3) {
                        System.exit(0);
                    }
                }

            }

        }


    }

    private void deletePerson() {

        watchAllPerson();

    }

    private void watchAllPerson() {


    }

    public void userPath(Person person) {
        System.out.println("Путь user");
    }

}
