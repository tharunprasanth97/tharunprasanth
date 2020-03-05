package com.hexaware.MLP205.model;

import com.hexaware.MLP205.persistence.DbConnection;
import com.hexaware.MLP205.persistence.EmployeeDao;
//import com.hexaware.MLP205.util.CliMain;

import java.util.Objects;
import java.util.List;

/**
 * Employee class to store employee personal details.
 * @author hexware
 */
public class Employee {

  /**
   * empId to store employee id.
   */
  private int emp_id;
  private String emp_name;
  private long emp_mobile;
  private String department;
  private String email;
  private String designation;
  private String user_name;
  private String passwordd;
  private int mgr_id;
  private int avail_leave;
  
  // Employee[] arr = new Employee[5];
  
  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Employee emp = (Employee) obj;
    if (Objects.equals(emp_id, emp.emp_id)&& 
    Objects.equals(emp_name, emp.emp_name)&&
    Objects.equals(emp_mobile, emp.emp_mobile)&&
    Objects.equals(department, emp.department)&&
    Objects.equals(email, emp.email)&&
    Objects.equals(designation, emp.designation)&&
    Objects.equals(user_name, emp.user_name)&&
    Objects.equals(passwordd,emp.passwordd)&&
    Objects.equals(mgr_id,emp.mgr_id)&&
    Objects.equals(avail_leave,emp.avail_leave)) {
      return true;
    }
    return false;
  } 
 
  @Override
  public final int hashCode() {
    return Objects.hash(emp_id,emp_name,emp_mobile,department,email,designation,user_name,passwordd,mgr_id,avail_leave);
  }


  @Override
  public String toString() {
    return    " Employee Id: " + emp_id + 
              "Employee name: " + emp_name + 
              "Employee Mobile Number: "+ emp_mobile +
              "Employee Email: " + email + 
              "Employee designation: " + designation +
              "Employee department: " + department +
              "Employee user_name: "+user_name+
              "Employee passwordd: " + passwordd+
              "Manager ID: "+mgr_id+
              "Available Leave"+avail_leave;
  }
  


  /**
   * @param argEmpId to initialize employee id.
   */
  public Employee(final int emp_id , final String emp_name, final long emp_mobile,final String email,final String designation,final String department,final String user_name,final String passwordd,final int mgr_id,final int avail_leave) {
    this.emp_id = emp_id;
    this.emp_name=emp_name;
    this.emp_mobile=emp_mobile;
    this.department=department;
    this.email=email;
    this.designation=designation;
    this.user_name=user_name;
    this.passwordd=passwordd;
    this.mgr_id=mgr_id;
    this.avail_leave=avail_leave;
  }   
    
  public Employee()
   {

    }


/**
   * Gets the EmployeeId.
   * 
   * @return this Employee's ID.
   */

  public final int getEmpId() {
     return emp_id;
  }
  public final String getemp_name()
  {
      return emp_name;
  }
  public final long getemp_mobile()
  {
     return emp_mobile;
  }
  
  public final String getDepartment()
  {
      return department;
  }
  public final String getEmail()
  {
      return email;
  }
  public final String getdesignation()
  {
    return designation;
  }
 
  public final String getUser_name()
  {
      return user_name;
  }
  public final String getPasswordd()
  {
      return passwordd;
  }
  public final int getManagerID(){
    return mgr_id;
  }
  public final int getAvailableLeave(){
    return avail_leave;
  }


  /**
   *
   * @param argEmpId to set employee id.
   */
  public final void setEmpId(final int argemp_id) {
    this.emp_id = argemp_id;
  }
  public final void setemp_name(final String emp_name)
   {
       this.emp_name=emp_name;
   }
   public final void setemp_mobile(final long emp_mobile)
   {
       this.emp_mobile=emp_mobile;
   }
   public final void setEmail(final String email)
   {
       this.email=email;
   }
   public final void setdesignation(final String designation)
   {
       this.designation=designation;
   }
   public final void setDepartment(final String department)
   {
       this.department=department;
   }
   
   public final void setUser_name(final String user_name)
   {
       this.user_name=user_name;
   }
   public final void setPasswordd(final String passwordd)
   {
       this.passwordd=passwordd;
   }
   public final void setManagetID(final int mgrid)
   {
     this.mgr_id=mgrid;
   }
   public final void setAvailableLeave(final int availleave)
   {
     this.avail_leave=availleave;
   }
      
  /**
   * The dao for employee.
   */
  private static EmployeeDao dao() {
    final DbConnection db = new DbConnection();
    return db.getConnect().onDemand(EmployeeDao.class);
  }



  /**
   * list all employee details.
   * @return all employees' details
   */
  public static Employee[] listAll() {

    final List<Employee> es = dao().list();
    return es.toArray(new Employee[es.size()]);
  }

  /**
   * list employee details by id.
   * @param EmpId id to get employee details.
   * @return Employee
   */
  public static Employee listById(final int emp_id) {
    return dao().find(emp_id);
  }

}