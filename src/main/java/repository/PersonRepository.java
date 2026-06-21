package repository;

import model.Person;

import java.util.Optional;

public interface PersonRepository {

    Optional<Person> enter(String login, String password);

}
