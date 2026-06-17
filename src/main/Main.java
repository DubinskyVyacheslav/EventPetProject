package main;

import model.Person;
import service.PersonService;
import util.ConnectionManager;
import util.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
        PersonService personService = new PersonService();
        personService.enter();

    }
}
//        try (Connection connection = ConnectionManager.getConnection()) {
//
//            String sql = "SELECT * FROM Employees";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet query = statement.executeQuery();
//
//            if (query.next()) {
//                String id = query.getString("id");
//                String login = query.getString("login");
//                String password = query.getString("password");
//                String name = query.getString("name");
//                String status = query.getString("name");
//                Person person = new Person(id, login, password, name, status);
//                System.out.println(person);
//            } else {
//                System.out.println("В таблице нет данных");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
