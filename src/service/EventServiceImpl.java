package service;

import model.Event;
import model.Person;
import repository.EventRepositoryImpl;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EventServiceImpl {

    static Scanner scanner = new Scanner(System.in);
    private static final EventRepositoryImpl eventRepository = new EventRepositoryImpl();

    public static void createEvent() {

        for (int attempts = 0; attempts < 5; attempts++) {
            System.out.print("Введите название ивента :");
            String eventName = scanner.nextLine().trim();
            if (eventName.isEmpty() || eventName.length() > 200) {
                System.out.println("Введено неправильно имя ивента");
                continue;
            }
            System.out.print("Введите дату ивента");
            LocalDateTime eventTime = createTimeEvent();
            System.out.print("Введите город в котором будет проходить Event : ");
            String townEvent = scanner.nextLine();
            if (townEvent.isEmpty() || townEvent.length() > 100) {
                System.out.println("Введено неправильное название города");
                continue;
            }
            eventRepository.createEvent(new Event(eventName, eventTime, "ACTIVE", townEvent));
            break;
        }


    }

    private static LocalDateTime createTimeEvent() {
        LocalDateTime eventTime = LocalDateTime.now();
        LocalDateTime timeBefore = LocalDateTime.now().plusDays(1);
        int count = 0;
        do {

            try {
                System.out.println(count > 0 ? "Неправильно введена дата" : "");
                System.out.print("Год: ");
                int year = Integer.parseInt(scanner.nextLine());
                System.out.print("Месяц: ");
                int month = Integer.parseInt(scanner.nextLine());
                System.out.print("День: ");
                int day = Integer.parseInt(scanner.nextLine());
                System.out.print("Час: ");
                int hour = Integer.parseInt(scanner.nextLine());
                System.out.print("Минуты: ");
                int minutes = Integer.parseInt(scanner.nextLine());

                eventTime = LocalDateTime.of(year, month, day, hour, minutes, 0);
            } catch (Exception e) {
                count++;
                System.out.println(e.getMessage());
                if (count == 3) {
                    System.out.println("Исчерпано кол-во попыток");
                    System.exit(0);
                }
            }

        } while (eventTime.isBefore(timeBefore));

        return eventTime;

    }

    public static void delete() {
        String deleteEventName = "";
        for (int attempts = 0; attempts < 5; attempts++) {
            System.out.println("Введите название мероприятия которые хотите удалить");
            System.out.print("Ввод : ");
            deleteEventName = scanner.nextLine();
            if (getEvent(deleteEventName).isEmpty()) {
                System.out.println("Event с таким названием не существует");
                continue;
            }
            EventRepositoryImpl.delete(deleteEventName);
            System.out.println("Успешно удалён");
            break;
        }
    }

    public static void redactEvent() {

        if (EventRepositoryImpl.getAllEvent().isEmpty()) {
            System.out.println("Мероприятий нету");
            return;
        }
        watchAllEvent();

        String eventName = "";
        Optional<Event> event = Optional.empty();
        flag:
        for (int attempts = 0; attempts < 5; attempts++) {
            System.out.println("Введите название мероприятия");
            System.out.print("Ввод : ");
            eventName = scanner.nextLine();
            event = getEvent(eventName);
            if (event.isPresent()) {

                System.out.println("Такое мероприятия существует");
                System.out.println("Хотите его изменить");
                System.out.println("""
                        1.Да
                        2.Нет
                        """);
                switch (scanner.nextLine().trim()) {
                    case "1", "да" -> {
                        break flag;
                    }
                    case "2", "нет" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Неверный ввод");
                        attempts++;
                        if (attempts > 3) System.exit(0);
                    }
                }
            } else {
                System.out.println("Такого мероприятия не существует");
                System.out.println("Хотите продолжить?");
                System.out.println("""
                        1.Да
                        2.Нет
                        """);
                switch (scanner.nextLine().trim().toLowerCase()) {
                    case "1", "да" -> {
                    }
                    case "2", "нет" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Неправильный ввод");
                        attempts++;
                        if (attempts > 3) System.exit(0);
                    }
                }
            }
        }

        System.out.println("Что хотите изменить?");
        System.out.println("""
                1.Название мероприятия
                2.Дату мероприятия
                3.Статус
                4.Город
                0.Ничего
                """);
        String step;
        for (int attempts = 0; true; attempts++) {

            if (attempts > 0) {
                System.out.println("Хотите ещё что-нибудь изменить?");
                System.out.println("""
                        1.Название мероприятия
                        2.Дату мероприятия
                        3.Статус
                        4.Город
                        0.Ничего
                        """);
            }
            System.out.print("Ввод : ");
            step = scanner.nextLine().trim();
            switch (step) {
                case "1", "название" -> redactNameEvent(eventName);
                case "2", "дату" -> redactDateEvent(event.get());
                case "3", "статус" -> redactStatusEvent(event.get());
                case "4", "город" -> redactTownEvent(event.get());
                case "0", "ничего" -> {
                    return;
                }
                default -> {
                    System.out.println("Неправильный ввод");
                    attempts++;
                    if (attempts > 3) System.exit(0);
                }
            }
        }
    }


    private static Optional<Event> getEvent(String eventName) {
        return EventRepositoryImpl.getEvent(eventName);
    }

    private static void redactNameEvent(String oldName) {
        System.out.println("На какое имя хотите изменить ");
        System.out.print("Ввод : ");
        String newName = scanner.nextLine().trim();
        EventRepositoryImpl.redactName(oldName, newName);

    }

    private static void redactTownEvent(Event event) {
        System.out.println("На какой город хотите изменить ?");
        System.out.print("Ввод : ");
        String newTown = scanner.nextLine().trim();
        EventRepositoryImpl.redactTownEvent(newTown, event.getId());
    }

    private static void redactStatusEvent(Event event) {
        System.out.println("Сейчас статус мероприятия " + event.getName() + " :" + event.getStatus());
        System.out.println("Хотите изменить статус ? ");
        System.out.println("""
                1.Да
                2.Нет
                """);
        flag:
        for (int attempts = 0; true; attempts++) {

            switch (scanner.nextLine().trim().toLowerCase()) {
                case "1", "да" -> {
                    if (event.getStatus().equals("ACTIVE")) {
                        EventRepositoryImpl.redactStatus(event.getId(), "NOTACTIVE");

                    } else {
                        EventRepositoryImpl.redactStatus(event.getId(), "ACTIVE");
                    }
                    break flag;
                }
                case "2", "нет" -> {
                    return;
                }
                default -> {
                    System.out.println("Неправильный ввод");
                    if (attempts > 3) System.exit(0);
                }
            }
        }


    }

    private static void redactDateEvent(Event event) {
        LocalDateTime newDate = createTimeEvent();
        EventRepositoryImpl.redactDateEvent(newDate, event.getId());

    }

    private static boolean checkRecordedEvent(Person person, Event event) {
        return EventRepositoryImpl.checkRecordedEvent(person, event);
    }

    public static void signUpForAnEvent(Person person) {

        if (EventRepositoryImpl.getAllEvent().isEmpty()) {
            System.out.println("Мероприятий нету");
            return;
        }
        System.out.println("Введите название мероприятия на которое хотите записаться");
        Optional<Event> event;
        watchAllEvent();
        flag:
        for (int attempts = 0; true; attempts++) {
            System.out.print("Ввод :");
            event = getEvent(scanner.nextLine());
            if (event.isPresent()) {
                System.out.println("Такое мероприятия существует");
                if (checkRecordedEvent(person, event.get())) {
                    System.out.println("Вы уже записаны на это мероприятие");
                    return;
                }
                System.out.println("Хотите на него записаться ?");
                System.out.println("""
                        1.Да
                        2.Нет
                        """);
                switch (scanner.nextLine().trim().toLowerCase()) {
                    case "1", "да" -> {
                        EventRepositoryImpl.signUpForAnEvent(person, event.get());
                        break flag;
                    }
                    case "2", "нет" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Неправильный ввод");
                        if (attempts > 3) System.exit(0);
                    }
                }
            } else {
                System.out.println("Такого мероприятия не существует");
                if (attempts > 3) System.exit(0);
            }
        }

    }

    public static void checkWhatEventsRecorded(Person person) {
        List<Event> eventList = EventRepositoryImpl.checkWhatEventsRecorded(person);
        if (!eventList.isEmpty()) {
            System.out.println("Ты записан на эти мероприятия");
            eventList.forEach(System.out::println);
        } else {
            System.out.println("Ты не записан на мероприятия !");
        }

    }

    public static void watchAllEvent() {
        List<Event> eventList = EventRepositoryImpl.getAllEvent();
        for (Event event : eventList) {
            System.out.println(event);
        }

    }

    public static void withdrawFromTheEvent(Person person) {
        List<Event> eventList = EventRepositoryImpl.checkWhatEventsRecorded(person);
        if (!eventList.isEmpty()) {
            eventList.forEach(System.out::println);
            System.out.println("С какого мероприятия вы хотите сняться ?");
            for (int i = 0; i < 4; i++) {
                System.out.println("Введите названия мероприятия с которого хотите сняться");
                System.out.print("Ввод :");
                try {
                    String eventName = scanner.nextLine().trim();
                    int eventId = eventList.stream()
                            .filter(e -> e.getName().equalsIgnoreCase(eventName))
                            .findFirst()
                            .get()
                            .getId();
                    EventRepositoryImpl.withdrawFromTheEvent(person, eventId);
                    System.out.println("Успешно!");
                    return;

                } catch (Exception e) {
                    System.out.println("Неверный ввод");
                    if (i == 3) {
                        System.exit(0);
                    }
                }

            }
        } else {
            System.out.println("Ты не записан на мероприятия !");
        }


    }
}




