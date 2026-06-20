package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@ToString
public class Person {

    private UUID uuid;
    private String login;
    private String name;
    private String status;


    public Person(UUID uuid, String login, String name, String status) {
        this.uuid = uuid;
        this.login = login;
        this.name = name;
        this.status = status;
    }

    public Person() {
    }
}
