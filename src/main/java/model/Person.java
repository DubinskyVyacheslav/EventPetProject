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
    private String password;
    private String status;


    public Person(UUID uuid, String login,String password, String status) {
        this.uuid = uuid;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public Person() {
    }

    public Person(UUID uuid, String loginFromDB, String status) {
        this.uuid = uuid;
        this.login = loginFromDB;
        this.status = status;
    }
}
