package org.hello;

import java.util.List;
import java.util.Scanner;
import com.tvd12.ezyfox.bean.EzyBeanContext;

public class Main {
    public static void main(String[] args) {
        final EzyBeanContext beanContext = EzyBeanContext.builder()
                .scan("org.hello")
                .build();
        final EmployeeDAO employeeDAO =
                beanContext.getBeanCast(EmployeeDAO.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. List Employees");
            System.out.println("2. Add Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Calculate Total Salary");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    List<Employee> employees = employeeDAO.listEmployees();
                    if (employees != null) {
                        for (Employee employee : employees) {
                            System.out.printf("Employee ID: %d, Name: %s, Salary: %.0f, Working Days: %.0f, Receipt: %.0f, Payment: %.0f%n",
                                    employee.getId(), employee.getName(), employee.getSalary(), employee.getWorkingdays(), employee.getReceipt(), employee.getPayment());
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    System.out.print("Enter working days: ");
                    double workingdays = scanner.nextDouble();
                    System.out.print("Enter receipt: ");
                    double receipt = scanner.nextDouble();
                    System.out.print("Enter payment: ");
                    double payment = scanner.nextDouble();
                    Employee employee = new Employee();
                    employee.setName(name);
                    employee.setSalary(salary);
                    employee.setWorkingdays(workingdays);
                    employee.setReceipt(receipt);
                    employee.setPayment(payment);
                    employeeDAO.addEmployee(employee);
                    break;
                case 3:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter new name: ");
                    name = scanner.next();
                    System.out.print("Enter new salary: ");
                    salary = scanner.nextDouble();
                    System.out.print("Enter new working days: ");
                    workingdays = scanner.nextDouble();
                    System.out.print("Enter new receipt: ");
                    receipt = scanner.nextDouble();
                    System.out.print("Enter new payment: ");
                    payment = scanner.nextDouble();
                    employeeDAO.updateEmployee(id, name, salary, workingdays, receipt, payment);
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    id = scanner.nextInt();
                    employeeDAO.deleteEmployee(id);
                    break;
                case 5:
                    System.out.println(employeeDAO.calculateTotalSalary());
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}