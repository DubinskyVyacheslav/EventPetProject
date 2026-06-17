package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Person {

    private String UUID;
    private String login;
    private String name;
    private String status;


    public Person(String UUID, String login, String name, String status) {
        this.UUID = UUID;
        this.login = login;
        this.name = name;
        this.status = status;
    }

    public Person() {
    }
}
