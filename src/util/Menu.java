package util;

import controller.PersonController;
import repository.EventRepositoryImpl;
import repository.PersonRepositoryImpl;
import service.EventServiceImpl;
import service.PersonService;

public class Menu {

    public static void start(){
        EventRepositoryImpl eventRepository = new EventRepositoryImpl();
        EventServiceImpl eventService = new EventServiceImpl(eventRepository);
        PersonRepositoryImpl personRepository = new PersonRepositoryImpl();
        PersonService personService = new PersonService(personRepository,eventService);
        PersonController personController = new PersonController(personService);
        personService.enter();
    }



}
