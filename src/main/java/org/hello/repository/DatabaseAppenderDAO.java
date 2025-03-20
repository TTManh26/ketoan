package org.hello.repository;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.hello.util.DatabaseUtil;
import org.hello.entity.DatabaseAppenderEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
@AllArgsConstructor
@EzySingleton
public class DatabaseAppenderDAO {
    private final DatabaseUtil databaseUtil;

    public void logToDatabase(DatabaseAppenderEntity logEntry) {
        Transaction transaction = null;
        try (Session session = databaseUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(logEntry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}