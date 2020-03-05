package com.hexaware.MLP205.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hexaware.MLP205.model.EnumLeaveStatus;
import com.hexaware.MLP205.model.EnumLeaveType;
import com.hexaware.MLP205.model.Leave;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class LeaveMapper implements ResultSetMapper<Leave> {
  
public final Leave map(final int idx,final ResultSet r,final StatementContext ctx)throws SQLException {
  
    String leave_type = r.getString("leave_type");
    String leave_status = r.getString("leave_status");
    EnumLeaveType lt = EnumLeaveType.valueOf(leave_type);
    EnumLeaveStatus ls = EnumLeaveStatus.valueOf(leave_status);
        return new Leave(lt,r.getString("leave_description"),ls,
        r.getDate("leave_from_date"),
        r.getDate("leave_to_date"),r.getDate("leave_applied_date"),
        r.getInt("no_leave"),r.getInt("emp_id"),
        r.getInt("leave_id"),r.getString("update_leave_status"),
        r.getInt("available_leave"));
        
        
    }

}
