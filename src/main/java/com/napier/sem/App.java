package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;


public class App {
    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if (args.length < 1) {
            a.connect("localhost:33060", 10000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        // Get Employee
        Employee emp = a.getEmployee(255530);
        // Display results
        a.displayEmployee(emp);

        // Extract employee salary information
        ArrayList<Employee> employees = a.getSalariesByRole();

        // Test the size of the returned data - should be 240124
        //System.out.println(employees.size());

        a.printSalariesByRole(employees);

        // Extract employee salary information
        ArrayList<Employee> employeesAll = a.getAllSalaries();

        // Test the size of the returned data - should be 240124
        //System.out.println(employees.size());

        a.printSalaries(employeesAll);

        //get list of departments
        ArrayList<Department> department = a.getDepartment();

        a.printGetDepartment(department);

        // Extract employee salary information
        ArrayList<Employee> employeesByDept = a.getSalariesByDepartment();

        // Test the size of the returned data - should be 240124
        //System.out.println(employees.size());

        a.printSalariesByDepartment(employeesByDept);


        // Disconnect from database
        a.disconnect();
    }



    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {
                    // Wait a bit for db to start
                    Thread.sleep(delay);
                }

                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

                // Let's wait before attempting to reconnect
                shouldWait = true;
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }
    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getSalariesByRole() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                            " FROM employees, salaries, titles " +
                            " WHERE employees.emp_no = salaries.emp_no " +
                            " AND employees.emp_no = titles.emp_no " +
                            " AND salaries.to_date = '9999-01-01' " +
                            " AND titles.to_date = '9999-01-01' " +
                            " AND titles.title = 'Engineer' " +
                            " ORDER BY employees.emp_no ASC LIMIT 10";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     *
     * @param employees The list of employees to print.
     */
    public void printSalariesByRole(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format(""));
        System.out.println(String.format("Salaries By Role"));
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees) {
            if (emp == null) {
                // Skip this iteration if emp is null
                continue;
            }
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC LIMIT 10";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employeesAll = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employeesAll.add(emp);
            }
            return employeesAll;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     *
     * @param employeesAll The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employeesAll)
        {
            // Check employees is not null
            if (employeesAll == null)
            {
                System.out.println("No employees");
                return;
            }
        // Print header
        System.out.println(String.format(""));
        System.out.println(String.format("Employee Salaries"));
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employeesAll) {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    public ArrayList<Department> getDepartment() {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT * FROM departments";

            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract department information
            ArrayList<Department> departments = new ArrayList<>();
            while (rset.next()) {
                Department dept = new Department();
                dept.dept_no = rset.getString("dept_no"); // Use column name directly
                dept.dept_name = rset.getString("dept_name"); // Use column name directly
                departments.add(dept);
            }
            return departments;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;
        }
    }


    public void printGetDepartment(ArrayList<Department> departments) {
        // Print header
        System.out.println(String.format(""));
        System.out.println(String.format("Department List"));
        System.out.println(String.format("%-10s %-15s ", "Dept No.", "Dept Name"));
        // Loop over all employees in the list
        for (Department dept : departments) {
            String dept_string =
                    String.format("%-10s %-15s ",
                            dept.dept_no, dept.dept_name);
            System.out.println(dept_string);
        }
    }



    public ArrayList<Employee> getSalariesByDepartment(){
    try {
        // Create an SQL statement
        Statement stmt = con.createStatement();
        // Create string for SQL statement
        String strSelect =
                "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                        "FROM employees, salaries, dept_emp, departments " +
                        "WHERE employees.emp_no = salaries.emp_no " +
                        "AND employees.emp_no = dept_emp.emp_no " +
                        "AND dept_emp.dept_no = departments.dept_no " +
                        "AND salaries.to_date = '9999-01-01' " +
                        "AND departments.dept_no = 'd009' " +
                        "ORDER BY employees.emp_no ASC LIMIT 10";

        // Execute SQL statement
        ResultSet rset = stmt.executeQuery(strSelect);
        // Extract employee information
        ArrayList<Employee> employeesByDept = new ArrayList<Employee>();
        while (rset.next()) {
            Employee emp = new Employee();
            emp.emp_no = rset.getInt("employees.emp_no");
            emp.first_name = rset.getString("employees.first_name");
            emp.last_name = rset.getString("employees.last_name");
            emp.salary = rset.getInt("salaries.salary");
            employeesByDept.add(emp);
        }
        return employeesByDept;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Failed to get salary details");
        return null;
    }
}
    /**
     * Prints a list of employees.
     *
     * @param employeesByDept The list of employees to print.
     */
    public void printSalariesByDepartment(ArrayList<Employee> employeesByDept)
    {
        // Check employees is not null
        if (employeesByDept== null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format(""));
        System.out.println(String.format("Salaries By Department"));
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employeesByDept) {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    public void addEmployee(Employee emp)
    {
        try
        {
            Statement stmt = con.createStatement();
            String strUpdate =
                    "INSERT INTO employees (emp_no, first_name, last_name, birth_date, gender, hire_date) " +
                            "VALUES (" + emp.emp_no + ", '" + emp.first_name + "', '" + emp.last_name + "', " +
                            "'9999-01-01', 'M', '9999-01-01')";
            stmt.execute(strUpdate);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to add employee");
        }
    }

}


