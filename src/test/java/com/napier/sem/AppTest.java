package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList; //had to put this in for the test with arraylist

import static org.junit.jupiter.api.Assertions.*;
public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printSalariesTestNull()
    {
        app.printSalaries(null);
    }

    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employeesAll = new ArrayList<Employee>();
        app.printSalaries(employeesAll);
    }

    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employeesAll = new ArrayList<Employee>();
        employeesAll.add(null);
        app.printSalaries(employeesAll);
    }

    @Test
    void printSalaries()
    {
        ArrayList<Employee> employeesAll = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employeesAll.add(emp);
        app.printSalaries(employeesAll);
    }

    @Test
    void printSalariesByRoleTestNull()
    {
        app.printSalariesByRole(null);
    }

    @Test
    void printSalariesByRoleTestEmpty()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        app.printSalariesByRole(employees);
    }

    @Test
    void printSalariesByRoleTestContainsNull()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(null);
        app.printSalariesByRole(employees);
    }

    @Test
    void printSalariesByRole()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalariesByRole(employees);
    }

    @Test
    void printSalariesByDepartmentTestNull()
    {
        app.printSalariesByDepartment(null);
    }

    @Test
    void printSalariesByDepartmentTestEmpty()
    {
        ArrayList<Employee> employeesByDept = new ArrayList<Employee>();
        app.printSalariesByDepartment(employeesByDept);
    }

    @Test
    void printSSalariesByDepartmentTestContainsNull()
    {
        ArrayList<Employee> employeesByDept = new ArrayList<Employee>();
        employeesByDept.add(null);
        app.printSalariesByDepartment(employeesByDept);
    }

    @Test
    void printSalariesByDepartment()
    {
        ArrayList<Employee> employeesByDept = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employeesByDept.add(emp);
        app.printSalariesByDepartment(employeesByDept);
    }



}

