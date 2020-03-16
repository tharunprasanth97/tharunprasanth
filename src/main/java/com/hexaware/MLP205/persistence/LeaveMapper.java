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
  
    String leave_type = r.getString("lt");
    String leave_status = r.getString("ls");
    EnumLeaveType leaveType = EnumLeaveType.valueOf(leave_type);
    EnumLeaveStatus leaveStatus = EnumLeaveStatus.valueOf(leave_status);
        return new Leave(leaveType,r.getString("leave_description"),leaveStatus,
        r.getDate("leave_from_date"),
        r.getDate("leave_to_date"),r.getDate("applied_leave"),
        r.getInt("emp_id"),
        r.getInt("leave_id"), r.getInt("available_leave"));
        
 
    }

}
