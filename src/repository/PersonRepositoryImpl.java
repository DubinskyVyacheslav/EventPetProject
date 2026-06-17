package repository;

import model.Person;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {

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
                String id = query.getString("id");
                String loginFromDB = query.getString("login");
                String status = query.getString("status");
                person = new Person(id, loginFromDB, password, status);
                return Optional.of(person);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();

    }

}
