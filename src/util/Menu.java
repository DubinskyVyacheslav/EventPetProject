package util;

import service.PersonService;

public class Menu {

    public void welcomUser(){

        PersonService personService = new PersonService();
        personService.enter();
    }

}
