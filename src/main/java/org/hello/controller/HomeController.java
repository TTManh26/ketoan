package org.hello.controller;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import lombok.AllArgsConstructor;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestCookie;
import com.tvd12.ezyhttp.server.core.view.Redirect;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.RequiredArgsConstructor;
import org.hello.entity.Employee;
import org.hello.entity.User;
import org.hello.repository.EmployeeDAO;
import org.hello.service.AuthenticationService;
import org.hello.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController {

    private AuthenticationService authenticationService;
    private UserService userService;
    private EmployeeDAO employeeDAO;

    @DoGet("/home")
    public Object home(
            @RequestCookie("accessToken") String accessToken,
            @RequestCookie(value = "language", defaultValue = "en") String language,
            @RequestParam(value = "lang", defaultValue = "") String langParam) {
        String selectedLang = langParam.isEmpty() ? language : langParam;

        if (accessToken == null || accessToken.isEmpty()) {
            return Redirect.builder()
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        Integer userId = authenticationService.verifyAccessToken(accessToken);
        if (userId == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        return View.builder()
                .locale(selectedLang)
                .template("home")
                .addVariable("lang", selectedLang)
                .addVariable("user", user)
                .addCookie("language", selectedLang)
                .build();
    }

    @DoGet("/add-employee")
    public Object addEmployee(
            @RequestCookie("accessToken") String accessToken,
            @RequestCookie(value = "language", defaultValue = "en") String language,
            @RequestParam(value = "lang", defaultValue = "") String langParam) {
        String selectedLang = langParam.isEmpty() ? language : langParam;

        if (accessToken == null || accessToken.isEmpty()) {
            return Redirect.builder()
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        Integer userId = authenticationService.verifyAccessToken(accessToken);
        if (userId == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        return View.builder()
                .locale(selectedLang)
                .template("add-employee")
                .addVariable("lang", selectedLang)
                .addVariable("user", user) // Thêm biến user
                .addCookie("language", selectedLang)
                .build();
    }

    @DoGet("/employee-list")
    public Object employeeList(
            @RequestCookie("accessToken") String accessToken,
            @RequestCookie(value = "language", defaultValue = "en") String language,
            @RequestParam(value = "lang", defaultValue = "") String langParam) {
        String selectedLang = langParam.isEmpty() ? language : langParam;

        if (accessToken == null || accessToken.isEmpty()) {
            return Redirect.builder()
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        Integer userId = authenticationService.verifyAccessToken(accessToken);
        if (userId == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        List<Employee> employees = employeeDAO.listEmployees();
        return View.builder()
                .locale(selectedLang)
                .template("employee-list")
                .addVariable("lang", selectedLang)
                .addVariable("user", user) // Thêm biến user
                .addVariable("employees", employees)
                .addCookie("language", selectedLang)
                .build();
    }

    @DoGet("/calculate-salaries")
    public Object calculateSalaries(
            @RequestCookie("accessToken") String accessToken,
            @RequestCookie(value = "language", defaultValue = "en") String language,
            @RequestParam(value = "lang", defaultValue = "") String langParam) {
        String selectedLang = langParam.isEmpty() ? language : langParam;

        if (accessToken == null || accessToken.isEmpty()) {
            return Redirect.builder()
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        Integer userId = authenticationService.verifyAccessToken(accessToken);
        if (userId == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        User user = userService.getUser(userId);
        if (user == null) {
            return Redirect.builder()
                    .addCookie("accessToken", "")
                    .addCookie("language", selectedLang)
                    .uri("/login?lang=" + selectedLang)
                    .build();
        }

        List<String> salaries = employeeDAO.listEmployees().stream()
                .map(employee -> {
                    double individualTotalSalary = (employee.getSalary() / employee.getWorkingdays()) + employee.getPayment() + (employee.getReceipt() * 0.1);
                    return String.format("Employee ID: %d, Name: %s, Individual Total Salary: %.2f",
                            employee.getId(), employee.getName(), individualTotalSalary);
                })
                .collect(Collectors.toList());

        return View.builder()
                .locale(selectedLang)
                .template("calculate-salaries")
                .addVariable("lang", selectedLang)
                .addVariable("user", user) // Thêm biến user
                .addVariable("salaries", salaries)
                .addCookie("language", selectedLang)
                .build();
    }
}