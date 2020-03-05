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


  /**
   * return all the details of the selected employee.
   * @param empID the id of the employee
   * @return the employee object
   */
  @SqlQuery("SELECT * FROM EMPLOYEE WHERE EmpId = :EmpId")
  @Mapper(EmployeeMapper.class)
  Employee find(@Bind("empid") int emp_id);

  /**
  * close with no args is used to close the connection.
  */
  @SqlUpdate("UPDATE Employee SET avail_leave=avail_leave +:no_leave WHERE emp_id=:emp_id")
@Mapper(EmployeeMapper.class)

  void increment(@Bind("emp_id") int emp_id, @Bind("no_leave") int no_leave);

  // decrement
@SqlUpdate("UPDATE Employee SET avail_leave=avail_leave -:no_leave WHERE emp_id=:emp_id")
@Mapper(EmployeeMapper.class)
void decrement(@Bind("emp_id")int emp_id,@Bind("no_leave")int no_leave);
  void close();
}