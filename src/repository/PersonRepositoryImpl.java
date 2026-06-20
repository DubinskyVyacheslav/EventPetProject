package repository;

import model.Person;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonRepositoryImpl implements PersonRepository {

    public static List<Person> getAllPerson() {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "SELECT * FROM event";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet query = statement.executeQuery();
            while (query.next()) {
                UUID uuid = UUID.fromString((String) query.getObject("id"));
                String login = query.getString("login");
                String name = query.getString("name");
                String status = query.getString("status");
                personList.add(new Person(uuid,login,name,status));
            }
            return personList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void delete(Person deletePerson) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "DELETE FROM employees WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, deletePerson.getName());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Person> enter(String login, String password) {

        try (Connection connection = ConnectionManager.getConnection()) {

            Person person;
            String sql = "SELECT * FROM Employees WHERE  login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet query = statement.executeQuery();
            if (query.next()) {
                UUID uuid = UUID.fromString(query.getString("id"));
                String loginFromDB = query.getString("login");
                String status = query.getString("status");
                person = new Person(uuid, loginFromDB, password, status);
                return Optional.of(person);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();

    }

}
