package org.hello;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "log_table")
public class DatabaseAppenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "logger", nullable = false)
    private String logger;

    @Column(name = "message", nullable = false)
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}