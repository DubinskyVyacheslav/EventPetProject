package controller;

import service.PersonService;

import java.util.Scanner;

public class PersonController {

    PersonService personService;

    Scanner scanner = new Scanner(System.in);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public void welcomePerson() {
        System.out.println("Здравствуйте !");
        System.out.println("""
                1.Войти
                2.Зарегистрироваться
                0.Выход
                """);
        for (int attempts = 0; attempts < 4; attempts++) {
            System.out.print("Ввод :");
            switch (scanner.nextLine().toLowerCase().trim()) {
                case "1", "войти" -> personService.enter();
                case "2", "зарегистрироваться" -> personService.create();
                case "0", "выход" -> System.exit(0);
                default -> {
                    System.out.println("Неверный ввод");
                    if (attempts == 3) System.exit(0);
                }
            }

        }

    }

}
