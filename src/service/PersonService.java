package service;

import model.Person;
import repository.EventRepositoryImpl;
import repository.PersonRepositoryImpl;
import util.Text;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PersonService {

    PersonRepositoryImpl personRepository;
    EventServiceImpl eventService ;
    Scanner scanner = new Scanner(System.in);
    Person person;

    public PersonService(PersonRepositoryImpl personRepository, EventServiceImpl eventService) {
        this.personRepository = personRepository;
        this.eventService = eventService;

    }

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

                case "1" -> eventService.createEvent(); // ГОТОВО
                case "2" -> eventService.delete(); //ГОТОВО
                case "3" -> eventService.redactEvent();
                case "4" -> eventService.checkWhatEventsRecorded(person); //ГОТОВО
                case "5" -> eventService.signUpForAnEvent(person);
                case "6" -> eventService.withdrawFromTheEvent(person);
                case "7" -> eventService.watchAllEvent();
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

        List<Person> personList = PersonRepositoryImpl.getAllPerson(); // Это часть проверки и вывода всех пользователей
        if (personList.isEmpty()) {
            System.out.println("Нету пользователей !");
            return;
        }
        for (Person person : personList) {
            System.out.println(person);
        }

        Person deletePerson = chooseDeletePerson(personList);
        if (deletePerson != null) {
            PersonRepositoryImpl.delete(deletePerson);
        } else {
            System.out.println("Никто не удален !");
        }

    }

    private Person chooseDeletePerson(List<Person> personList) {

        for (int attempts = 0; attempts < 4; attempts++) {
            System.out.println("Введите имя пользователя которого хотите удалить");
            System.out.print("Ввод :");
            String personName = scanner.nextLine();
            try {
                Person deletePerson = personList.stream()
                        .filter(person -> person.getName().equalsIgnoreCase(personName))
                        .findFirst()
                        .get();
                return deletePerson;
            } catch (Exception e) {

                System.out.println("Неверный ввод");
                if (attempts == 3) return null;
            }
        }
        return null;

    }

    private void watchAllPerson() {
        List<Person> personList = PersonRepositoryImpl.getAllPerson();
        if (personList.isEmpty()) {
            return;
        }
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    public void userPath(Person person) {
        System.out.println("Путь user");
    }

    public void create() {
    }
}
