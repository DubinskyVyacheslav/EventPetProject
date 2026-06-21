package repository;

import model.Event;
import model.Person;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EventRepositoryImpl {

    public static void delete(String deleteEventName) {

        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "DELETE FROM event WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, deleteEventName);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Optional<Event> getEvent(String eventName) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "SELECT * FROM event WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, eventName);
            ResultSet query = statement.executeQuery();
            if (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                LocalDateTime evenDate = query.getTimestamp("event_date").toLocalDateTime();
                String status = query.getString("status");
                String town = query.getString("town");
                return Optional.of(new Event(id, name, evenDate, status, town));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public static void redactName(String oldName, String newName) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET name = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void redactTownEvent(String newTown, int eventId) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET town = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newTown);
            statement.setInt(2, eventId);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void redactStatus(int id, String status) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void redactDateEvent(LocalDateTime newDate, int id) {

        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET event_date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(newDate));
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void signUpForAnEvent(Person person, Event event) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET participant_ids = array_append(participant_ids, ?) WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, person.getUuid());
            statement.setInt(2, event.getId());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean checkRecordedEvent(Person person, Event event) {  // Проверяет записн ли человек на определенное мероприятие

        String sql = "SELECT ? = ANY(participant_ids) AS registered FROM event WHERE id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, person.getUuid());
            statement.setInt(2, event.getId());

            ResultSet query = statement.executeQuery();
            if (query.next()) {
                return query.getBoolean("registered");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<Event> checkWhatEventsRecorded(Person person) {   // Даёт List мероприятий на которые записан человек
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "SELECT * FROM event WHERE ? = ANY(participant_ids)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, person.getUuid());
            ResultSet query = statement.executeQuery();
            while (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                LocalDateTime evenDate = query.getTimestamp("event_date").toLocalDateTime();
                String status = query.getString("status");
                String town = query.getString("town");
                events.add(new Event(id, name, evenDate, status, town));
            }
            return events;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Event> getAllEvent() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "SELECT * FROM event";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet query = statement.executeQuery();
            while (query.next()) {
                int id = query.getInt("id");
                String name = query.getString("name");
                LocalDateTime evenDate = query.getTimestamp("event_date").toLocalDateTime();
                String status = query.getString("status");
                String town = query.getString("town");
                events.add(new Event(id, name, evenDate, status, town));
            }
            return events;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void withdrawFromTheEvent(Person person, int eventid) {

        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "UPDATE event SET participant_ids = array_remove(participant_ids, ?) WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, person.getUuid());
            statement.setObject(2, eventid);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void createEvent(Event event) {
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "INSERT INTO event(name,event_date,status,town) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, event.getName());
            statement.setTimestamp(2, Timestamp.valueOf(event.getDateEvent()));
            statement.setString(3, event.getStatus());
            statement.setString(4, event.getTown());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
