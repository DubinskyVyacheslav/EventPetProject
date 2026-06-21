package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private int id;
    private String name;
    private LocalDateTime dateEvent;
    private String status;
    private String town;

    public Event(String eventName, LocalDateTime eventTime, String status, String townEvent) {
        this.name = eventName;
        this.dateEvent = eventTime;
        this.status = status;
        this.town = townEvent;
    }

    public String toString() {
        return "Event(id=" + this.getId() + ", name=" + this.getName() + ", dateEvent=" + this.getDateEvent() + ", status=" + this.getStatus() + ", town=" + this.getTown() + ")";
    }
}
