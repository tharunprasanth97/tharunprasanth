package com.hexaware.MLP205.persistence;


import java.util.List;

import com.hexaware.MLP205.model.Leave;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;


public interface LeaveDao{   
    
  @SqlQuery("select * from leave where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  List<Leave> list();
    
  @SqlQuery("select * from leave where leave_id = :leave_id")
  @Mapper(LeaveMapper.class)
  LeaveDao find(@Bind("leave_id") int leave_id);

  @SqlQuery("select designation,leave_id from employee,leave where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  LeaveDao show(@Bind("leave_id") int leave_id,@Bind("designation")String designation);

  
}
