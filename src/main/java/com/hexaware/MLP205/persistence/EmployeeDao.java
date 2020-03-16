package com.hexaware.MLP205.persistence;

import com.hexaware.MLP205.model.Employee;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;


import java.util.List;

/**
 * The DAO class for employee.
 */
public interface EmployeeDao  {
  /**
   * return all the details of all the employees.
   * @return the employee array
   */
  @SqlQuery("SELECT * FROM EMPLOYEE")
  @Mapper(EmployeeMapper.class)
  List<Employee> list();



  @SqlUpdate("Insert into Employee (emp_id,emp_name,emp_mobile,Department,email,user_name,passwordd,designation,mgr_id,avail_leave) values(:emp_id, :emp_name, :emp_mobile, :Department , :email,:designation,:user_name,:passwordd,:mgr_id,avail_leave")
  public int insertEmployee(@Bind("emp_id") int emp_id, 
                            @Bind("emp_name") String emp_name, 
                            @Bind("emp_mobile") long emp_mobile,
                            @Bind("Department") String Department,
                            @Bind("email") String email,
                            @Bind("user_name") String user_name,
                            @Bind("passwordd") String passwordd,
                            @Bind("designation") String designation,
                            @Bind("mgr_id") int mgr_id,
                            @Bind("avail_leave")int avail_leave
                            );


  /**
   * return all the details of the selected employee.
   * @param empID the id of the employee
   * @return the employee object
   */
  @SqlQuery("SELECT * FROM EMPLOYEE WHERE emp_id = :emp_id")
  @Mapper(EmployeeMapper.class)
  Employee find(@Bind("emp_id") int emp_id);

// update employee

  @SqlUpdate("update Employee set email = :email where emp_id = :emp_id")
  int updateEmployee(@Bind("email") String email, @Bind("emp_id") int emp_id);

  @SqlUpdate("UPDATE employee SET avail_leave = avail_leave -:leaveTaken WHERE emp_id = :emp_id")
  void decrement(@Bind("emp_id") int emp_iD, @Bind("avail_leave") int avail_leave);


// insert employee

  
}