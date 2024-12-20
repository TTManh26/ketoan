package org.hello;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "workingdays", nullable = false)
    private double workingdays;

    @Column(name = "receipt", nullable = false)
    private double receipt;

    @Column(name = "payment", nullable = false)
    private double payment;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getWorkingdays() {
        return workingdays;
    }

    public void setWorkingdays(double workingdays) {
        this.workingdays = workingdays;
    }

    public double getReceipt() {
        return receipt;
    }

    public void setReceipt(double receipt) {
        this.receipt = receipt;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", workingdays=" + workingdays +
                ", receipt=" + receipt +
                ", payment=" + payment +
                '}';
    }
}