package com.hexaware.MLP205.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.StatementContext;

import com.hexaware.MLP205.model.Leave;

public class LeaveMapper implements ResultSetMapper<Leave> {
  
public final Leave map(final int idx,final ResultSet r,final StatementContext ctx)throws SQLException {
    
    return new Leave(r.getString("leave_type"),r.getString("leave_description"),r.getString("leave_status"),r.getDate("leave_from_date"),r.getDate("leave_to_date"),r.getDate("leave_applied_date"),r.getInt("no_leave"),r.getString("update_leave_status"),r.getInt("emp_id"),r.getInt("leave_id"),r.getInt("available_leave"));
        
    }

}