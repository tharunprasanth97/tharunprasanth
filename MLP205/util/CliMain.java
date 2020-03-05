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
    
    CliMain m=new CliMain();
    m.login();
    m.mainMenu();
  }
  


  private void mainMenu() {
    System.out.println("Leave Management System");
    System.out.println("-----------------------");
    System.out.println("1. List All Employees Info");
    System.out.println("2. Display Employee Info");
    System.out.println("3. Leave History");
    System.out.println("4. Update Leave ");
    System.out.println("5. Apply Leave");
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
        leaveHistory();
        break;
      //   case 4:
      //   CancelLeave();
      //   break;
      // case 5:
      //   applyLeave();
      //   break;
      // case 6:
      //   pendingLeaves();
      //   break;
      // case 7:
      //   approveDeny();
      //   break;
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
// ----------------------------------------------------------------------------------------
// list all employees info
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
// ----------------------------------------------------------------------------------------
// display employee info
  private void listEmployeesDetails() {
    Employee[] employee = Employee.listAll();
    for (Employee e : employee) {
      System.out.println(e.getEmpId() + "" + e.getemp_name() + "" + e.getemp_mobile() + "" + e.getEmail() + ""
          + e.getdesignation() + "" + e.getDepartment() + "" + e.getUser_name() + "" + e.getPasswordd());
    }
  }

// ----------------------------------------------------------------------------------------
// leave history
  private void leaveHistory(){
    int emp_id=0;
    while(true){
      System.out.println("Enter an Employee Id");
      String emp_Id =option.next();
      try{
        emp_id =Integer.parseInt(emp_Id);
        Leave[] leaves=Leave.leaveHistory(emp_id);
        if(leaves.length == 0){
          System.out.println("Wrong employee id");
        }else{
          System.out.println();
          for(Leave e: leaves){
            System.out.println(e.getLeaveID()+"  Emoloyee ID: "+e.getEmployeeID()+" From Date: "+e.getLeaveFromDate()+" To Date: "+e.getLeaveToDate()+" No of Days: "+e.getNoLeave()+" Leave Type: "+e.getLeaveType()+" Leave Status: "+e.getStatus()+" Leave Description: "+e.getLeaveDescription());
          }
        }
      }catch(NumberFormatException nfe){
        System.out.println("---------please enter correct employee id--------");
        listEmployeeDetail();
      }catch(NullPointerException e){
        System.out.println();
        System.out.println("---please enter correct employee id----------------");
        listEmployeeDetail();
      }
    
    }
  }
  // ---------------------------------------------------------------------------------------
  // apply leave
  public static void approveDeny() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter a leave ID:");
    int leaveId = sc.nextInt();
    System.out.println("Enter Employ Id  ");
    int mgrId = sc.nextInt();
    System.out.println("Decide what you want to do (APPROVE / DENY): ");
    String ch = sc.next();
    System.out.println("Manager Comments  ");
    sc.nextLine();
    String mgrCom = sc.nextLine();
    String res = Leave.approveDeny(leaveId, mgrId, ch, mgrCom);
    System.out.println(res);
    sc.close();
  }

  private void applyLeave() throws java.text.ParseException {
    int empid = 0;
    while (true) {
      System.out.println("Enter an Employee Id");
      String empId = option.next();
      try {
        empid = Integer.parseInt(empId);
        Employee.listById(empid).getEmpId();
        break;
      } catch (NumberFormatException nfe) {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("-----Please enter correct employee id------");
        System.out.println("-------------------------------------------");
      } catch (NullPointerException e) {
        System.out.println();
        System.out.println("--------------No Such Employee---------------");
        System.out.println("---------------------------------------------");
        System.out.println("-----Please enter correct employee id--------");
        System.out.println("---------------------------------------------");
      }
    }
    System.out.println();
    System.out.println("Your available leave balance is: " + Employee.listById(empid).getAvailableLeave());
    String endDate = null;
    String startDate = null;
    while (true) {
      try {
        System.out.println("Enter Start Date of your leave (yyyy-MM-dd): ");
        startDate = option.next();
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        sdfrmt.parse(startDate);
        System.out.println("Enter End Date of your leave (yyyy-MM-dd): ");
        endDate = option.next();
        SimpleDateFormat sdfrm = new SimpleDateFormat("yyyy-MM-dd");
        sdfrm.setLenient(false);
        sdfrm.parse(endDate);
        break;
      } catch (ParseException ex) {
        System.out.println("------------------------------");
        System.out.println("Please enter in correct date/format.");
        System.out.println("------------------------------");
      }
    }
    System.out.println("Enter the number of days you want leave for: ");
    int noOfdays = option.nextInt();
    System.out.println("Enter the type of leave you want. (EL / SL / ML): ");
    String leaveType = option.next();
    System.out.println("Please mention the reason for taking leave: ");
    String leaveReason = option.next();
    String res = null;
    try {
      res = Leave.applyLeave(empid, startDate,
                    endDate, noOfdays, leaveType.toUpperCase(),
                    leaveReason
      );
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(res);
  }

  // ---------------------------------------------------------------------------------------
  // update leave
 
  private void updateLeave() throws ParseException, java.text.ParseException {
    int leaveid = 0;
    int empid = 0;
    while (true) {
      System.out.println("Enter an employee Id");
      String empId = option.next();
      System.out.println("Enter an Leave Id");
      String leaveId = option.next();
      try {
        empid = Integer.parseInt(empId);
        leaveid = Integer.parseInt(leaveId);
        Leave.listById(leaveid).getEmployeeID();
        System.out.println(Leave.listById(leaveid).getEmployeeID());
        break;
      } catch (NumberFormatException nf) {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("-----Please enter correct employee id------");
      } catch (NullPointerException x) {
        System.out.println();
        System.out.println("--------------No Such Employee---------------");
        System.out.println("---------------------------------------------");
        System.out.println("-----Please enter correct employee id--------");
      }
    }
    System.out.println();
    System.out.println("Your available leave balance is: " + Employee.listById(empid).getAvailableLeave());
    String eDate = null;
    String sDate = null;
    while (true) {
      try {
        System.out.println("Enter Start Date of your leave (yyyy-MM-dd): ");
        sDate = option.next();
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        sdfrmt.parse(sDate);
        System.out.println("Enter End Date of your leave (yyyy-MM-dd): ");
        eDate = option.next();
        SimpleDateFormat sdfrm = new SimpleDateFormat("yyyy-MM-dd");
        sdfrm.setLenient(false);
        sdfrm.parse(eDate);
        break;
      } catch (ParseException exp) {
        System.out.println("------------------------------");
        System.out.println("Please enter in correct date/format.");

      }
    }
    System.out.println("Enter the number of days you want leave for: ");
    int ndays = option.nextInt();
    System.out.println("Enter the type of leave you want. (EL / SL / ML): ");
    String lType = option.next();
    System.out.println("Please mention the reason for taking leave: ");
    String lReason = option.next();
    String r = null;
    try {
      r = Leave.updateLeave(empid, sDate,
                    eDate, ndays, lType.toUpperCase(),
                    lReason, leaveid
      );
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(r);
  }

}
 
