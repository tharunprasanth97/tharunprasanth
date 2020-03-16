package com.hexaware.MLP205.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import com.hexaware.MLP205.model.Employee;
/**
 * Mapper class to map from result set to employee object.
 */
public class EmployeeMapper implements ResultSetMapper<Employee> {
  /**
   * @param idx the index
   * @param rs the resultset
   * @param ctx the context
   * @return the mapped employee object
   * @throws SQLException in case there is an error in fetching data from the resultset
   */
  public final Employee map(final int idx, final ResultSet rs, final StatementContext ctx) throws SQLException {

    return new Employee(rs.getInt("emp_id"),rs.getString("emp_name"),rs.getLong("emp_mobile"), rs.getString("Department"),rs.getString("email"),rs.getString("user_name"),rs.getString("passwordd"),rs.getString("designation"),rs.getInt("mgr_id"),rs.getInt("avail_leave"));    

    /**
     * @return Employee
     */
   
  }
}