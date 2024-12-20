package org.hello;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
@EzySingleton
@AllArgsConstructor
public class EmployeeDAO {
    private final DatabaseUtil databaseUtil;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);
    public void addEmployee(Employee employee) {
        executeTransaction(session -> {
            session.save(employee);
            logger.info("Added new employee: {}", employee);
        });
    }

    public List<Employee> listEmployees() {
        try (Session session = databaseUtil.getSessionFactory().openSession()) { // Sử dụng DatabaseUtil.getInstance()
            logger.info("Fetching all employees...");
            return session.createQuery("from Employee", Employee.class).list();
        } catch (Exception e) {
            logger.error("Error fetching employees: {}", e.getMessage(), e);
            return null;
        }
    }

    public void updateEmployee(int id, String name, double salary, double workingdays, double payment, double receipt) {
        executeTransaction(session -> {
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                employee.setName(name);
                employee.setSalary(salary);
                employee.setWorkingdays(workingdays);
                employee.setPayment(payment);
                employee.setReceipt(receipt);
                session.update(employee);
                logger.info("Updated employee with ID: {}", id);
            } else {
                logger.warn("Employee not found with ID: {}", id);
            }
        });
    }

    public void deleteEmployee(int id) {
        executeTransaction(session -> {
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);
                logger.info("Deleted employee with ID: {}", id);
            } else {
                logger.warn("Employee not found with ID: {}", id);
            }
        });
    }

    public double calculateTotalSalary() {
        double totalSalary = 0;
        try (Session session = databaseUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("from Employee", Employee.class).list();

            for (Employee employee : employees) {
                double salary = employee.getSalary();
                double workingDays = employee.getWorkingdays();
                double receipt = employee.getReceipt();
                double payment = employee.getPayment();

                double individualTotalSalary = (salary / workingDays) + payment + (receipt * 0.1);

                System.out.printf("Employee ID: %d, Name: %s, Total Salary: %.0f%n",
                        employee.getId(), employee.getName(), individualTotalSalary);
            }
        } catch (Exception e) {
            logger.error("Error calculating total salary: {}", e.getMessage(), e);
        }
        return totalSalary;
    }

    private void executeTransaction(TransactionAction action) {
        Transaction transaction = null;
        try (Session session = databaseUtil.getSessionFactory().openSession()) { // Sử dụng DatabaseUtil.getInstance()
            transaction = session.beginTransaction();
            action.execute(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Transaction failed: {}", e.getMessage(), e);
        }
    }

    @FunctionalInterface
    private interface TransactionAction {
        void execute(Session session);
    }
}