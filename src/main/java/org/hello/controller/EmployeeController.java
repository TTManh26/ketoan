package org.hello.controller;

import org.hello.entity.Employee;
import org.hello.repository.EmployeeDAO;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import java.util.stream.Collectors;

import java.util.List;

@Controller("/api/employees")
public class EmployeeController {

    @EzyAutoBind
    private EmployeeDAO employeeDAO;

    @DoGet
    public List<Employee> getAllEmployees() {
        return employeeDAO.listEmployees();
    }

    @DoPost("/add")
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        employeeDAO.addEmployee(employee);
        return ResponseEntity.ok("Employee added successfully");
    }

    @DoPut("/{id}")
    public ResponseEntity updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employee) {
        employeeDAO.updateEmployee(
                id,
                employee.getName(),
                employee.getSalary(),
                employee.getWorkingdays(),
                employee.getReceipt(),
                employee.getPayment());
        return ResponseEntity.ok("Employee updated successfully");
    }

    @DoDelete("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        employeeDAO.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @DoGet("/individual-total-salary")
    public List<String> calculateIndividualTotalSalaries() {
        return employeeDAO.listEmployees().stream()
                .map(employee -> {
                    double individualTotalSalary = (employee.getSalary() / employee.getWorkingdays()) + employee.getPayment() + (employee.getReceipt() * 0.1);
                    return String.format("Employee ID: %d, Name: %s, Individual Total Salary: %.2f",
                            employee.getId(), employee.getName(), individualTotalSalary);
                })
                .collect(Collectors.toList());
    }
}