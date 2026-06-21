package repository;

import lombok.extern.slf4j.Slf4j;
import model.Person;
import util.CipherPasswordBCrypt;
import util.ConnectionManager;
import util.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
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
                String status = query.getString("status");
                personList.add(new Person(uuid,login,status));
            }
            return personList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void delete(Person deletePerson) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "DELETE FROM employees WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, deletePerson.getLogin());
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
                person = new Person(uuid, loginFromDB, status);
                return Optional.of(person);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();

    }

    public String originalsName(String login) {

        String name = " ";
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT login FROM employees WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet execute = statement.executeQuery();
            if (execute.next()) {
                return execute.getString("login");
            }
        } catch (Exception e) {
            log.error(Text.getTextLogError(this.getClass()));
            throw new RuntimeException(e.getMessage());
        }
        return name;

    }

    public void create(Person person) {

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO employees(id,login,password,status) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, person.getUuid());
            statement.setString(2, person.getLogin());
            statement.setString(3, CipherPasswordBCrypt.hashingPassword(person.getPassword()));
            statement.setString(4, person.getStatus());
            statement.execute();
        } catch (Exception e) {
            log.error(Text.getTextLogError(this.getClass()));
            throw new RuntimeException(e.getMessage());
        }

    }
}
