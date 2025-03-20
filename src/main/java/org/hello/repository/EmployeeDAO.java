package org.hello.repository;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.hello.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@EzySingleton
@AllArgsConstructor
public class EmployeeDAO {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
        logger.info("Added new employee: {}", employee);
    }

    public List<Employee> listEmployees() {
        logger.info("Fetching all employees...");
        return employeeRepository.findAll();
    }

    public void updateEmployee(int id, String name, double salary, double workingDays, double payment, double receipt) {
        Employee employee = employeeRepository.findById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setSalary(salary);
            employee.setWorkingdays(workingDays);
            employee.setPayment(payment);
            employee.setReceipt(receipt);
            employeeRepository.save(employee);
            logger.info("Updated employee with ID: {}", id);
        } else {
            logger.warn("Employee not found with ID: {}", id);
        }
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id);
        if (employee != null) {
            employeeRepository.deleteById(id);
            logger.info("Deleted employee with ID: {}", id);
        } else {
            logger.warn("Employee not found with ID: {}", id);
        }
    }

    public double calculateTotalSalary() {
        List<Employee> employees = employeeRepository.findAll();
        double totalSalary = 0;
        for (Employee employee : employees) {
            double salary = employee.getSalary();
            double workingDays = employee.getWorkingdays();
            double receipt = employee.getReceipt();
            double payment = employee.getPayment();

            double individualTotalSalary = (salary / workingDays) + payment + (receipt * 0.1);
            System.out.printf("Employee ID: %d, Name: %s, Total Salary: %.0f%n",
                    employee.getId(), employee.getName(), individualTotalSalary);

            totalSalary += individualTotalSalary;
        }
        return totalSalary;
    }
}