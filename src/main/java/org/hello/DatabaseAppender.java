package org.hello;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class DatabaseAppender extends AppenderBase<ILoggingEvent> {
    private String url;
    private String user;
    private String password;
    private String driverClass;

    // Setters for parameters
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            DatabaseAppenderEntity logEntity = new DatabaseAppenderEntity();
            logEntity.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
            logEntity.setLevel(eventObject.getLevel().toString());
            logEntity.setLogger(eventObject.getLoggerName());
            logEntity.setMessage(eventObject.getFormattedMessage());

            //DatabaseAppenderDAO dao = new DatabaseAppenderDAO();
            //dao.logToDatabase(logEntity);

        } catch (Exception e) {
            addError("Failed to log to database", e);
        }
    }
}