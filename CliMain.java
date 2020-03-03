package com.hexaware.MLP205.util;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.text.DateFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Scanner;


import com.google.protobuf.TextFormat.ParseException;
import com.hexaware.MLP205.model.Employee;
import com.hexaware.MLP205.model.Leave;
import com.hexaware.MLP205.persistence.LeaveDao;
import java.text.ParseException;
import org.glassfish.jersey.server.ParamException;

/**
 * Class CliMain provides the command line interface to the leavemanagement
 * application.
 */
public class CliMain {
  private Scanner option = new Scanner(System.in, "UTF-8");

  /**
   * *main entry point
   * 
   * @throws java.text.ParseException
   */

  public static void main(final String[] args) throws ParseException, java.text.ParseException {
    
    LocalDate cdate = LocalDate.now(); // Create a date object
    System.out.println(cdate);

    // Create SimpleDateFormat object 
    SimpleDateFormat 
    sdfo 
    = new SimpleDateFormat("yyyy-MM-dd"); 
Scanner sc=new Scanner(System.in);
// Get the two dates to be compared 
String a;
String b;
System.out.println("Enter from date");
a= sc.nextLine();
Date d1 = sdfo.parse(a); 
System.out.println("Enter to date");
b= sc.nextLine();
Date d2 = sdfo.parse(b); 

// Print the dates 
System.out.println("Date1 : " + sdfo.format(d1)); 
System.out.println("Date2 : " + sdfo.format(d2)); 

// Compare the dates using compareTo() 
if (d1.compareTo(d2) > 0) { 

    // When Date d1 > Date d2 
    System.out.println("Date1 is after Date2"); 
} 

else if (d1.compareTo(d2) < 0) { 

    // When Date d1 < Date d2 
    System.out.println("Date1 is before Date2"); 
} 

else if (d1.compareTo(d2) == 0) { 

    // When Date d1 = Date d2 
    System.out.println("Date1 is equal to Date2"); 
} 
//     Scanner scanner = new Scanner(System.in);
    
//     System.out.println("Enter the Date yyyy-mm-dd ");

// String date = scanner.next();

// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
// Date b;
// // Parsing the String

// b = dateFormat.parse(date);
//   System.out.println(dateFormat.format(b));

// if(b.equals(date)){
//   System.out.println("equal");
// }else{
//   System.out.println("not equal");
// }

  }
  


  private void mainMenu() {
    System.out.println("Leave Management System");
    System.out.println("-----------------------");
    System.out.println("1. List All Employees Info");
    System.out.println("2. Display Employee Info");
    System.out.println("3. Apply Leave");
    System.out.println("4. Update Leave ");
    System.out.println("5. Emp Leave History");
    System.out.println("6. Pending Leave Status");
    System.out.println("7. Approve / Deny");
    System.out.println("8. Exit");
    System.out.println("Enter your choice:");
    int menuOption = option.nextInt();
    mainMenuDetails(menuOption);
  }

  private void mainMenuDetails(final int selectedOption) {
    switch (selectedOption) {
      case 1:
        listEmployeesDetails();
        break;
      case 2:
        listEmployeeDetail();
        break;
      case 3:
        applyLeave();
        break;
      case 4:
        updateLeave();
        break;
      case 5:
        leaveHistory();
        break;
      case 6:
        pendingLeaves();
        break;
      case 7:
        approveDeny();
        break;
      case 8:
        // halt since normal exit throws a stacktrace due to jdbc threads not responding
        Runtime.getRuntime().halt(0);
      default:
        System.out.println("Choose either 1, 2 or 3");
    }
    mainMenu();
  }

  
  /**
   * The main entry point.
   * 
   * @param ar the list of arguments
   */

  public void login() {
    System.out.println("Enter user name");
    // Scanner sc=new Scanner(System.in);
    final int n = option.nextInt();
    System.out.println("Enter passwordd");
    final String str = option.next();

    if ((n == 51000) && (str.equals("password"))) {
      System.out.println("You are successfully logged in");
      mainMenu();
    } else {
      System.out.println("Invalid Username or Password");
    }
  }

  private void listEmployeeDetail() {
    System.out.println("Enter an Employee Id");
    final int empId = option.nextInt();
    System.out.println(empId);
    final Employee employee = Employee.listById(empId);
    if (employee == null) {
      System.out.println("Sorry, No such employee");
    } else {
      System.out.println(employee.getEmpId() + "" + employee.getemp_name() + "" + employee.getemp_mobile() + ""
          + employee.getEmail() + "" + employee.getdesignation() + "" + employee.getDepartment() + ""
          + employee.getUser_name() + "" + employee.getPasswordd());
    }
  }

  private void listEmployeesDetails() {
    Employee[] employee = Employee.listAll();
    for (Employee e : employee) {
      System.out.println(e.getEmpId() + "" + e.getemp_name() + "" + e.getemp_mobile() + "" + e.getEmail() + ""
          + e.getdesignation() + "" + e.getDepartment() + "" + e.getUser_name() + "" + e.getPasswordd());
    }
  }

  private void leaveHistory() {
    System.out.println("Enter an Employee Id");
    final int leave_id = option.nextInt();
    System.out.println(leave_id);
    final LeaveDao leave = Leave.listleaveid(leave_id);
    if (leave == null) {
      System.out.println("Sorry, No such employee");
    } else {
      System.out.println(leave.getLeaveID()+""+leave.getEmployeeID()+""+leave.getLeaveFromDate()+""+leave.getLeaveToDate()+""+leave.getStatus()
          + "" + leave.getLeaveType() + "" + leave.getLeaveDescription() );
    }
  }
  
}