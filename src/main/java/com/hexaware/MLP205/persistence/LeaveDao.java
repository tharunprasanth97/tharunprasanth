package com.hexaware.MLP205.persistence;

import java.sql.Date;
import java.util.List;

import com.hexaware.MLP205.model.EnumLeaveStatus;
import com.hexaware.MLP205.model.EnumLeaveType;
import com.hexaware.MLP205.model.Leave;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;


public interface LeaveDao{   
    
  @SqlQuery("select * from Leavess where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  Leave list(@Bind("emp_id")int emp_id);
    
  @SqlQuery("select * from Leavess where leave_id = :leave_id")
  @Mapper(LeaveMapper.class)
  Leave find(@Bind("leave_id") int leave_id);
  

  @SqlQuery("select designation,leave_id from employee,leavess where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  Leave show(@Bind("leave_id") int leave_id,@Bind("designation")String designation);

 @SqlQuery("select * from leavess where emp_id =:emp_id")
 @Mapper(LeaveMapper.class)
 List<Leave>leaveHistory(@Bind("emp_id")int emp_id);
 

    @SqlUpdate("UPDATE LEAVESS SET COMMENTS = :leavemanagercomm, LEAVE_STATUS = :leavestatus WHERE "
      + "leave_id = :leaveid")
    void comment(@Bind("leavemanagercomm") String leavemanagercomm, @Bind("leave_status") String leavestatus,
    @Bind("leave_id") int leave_id);

    @SqlUpdate("Update leavess set leave_from_date =:leave_manager,leave_to_date =:leave_to_date,avail_balance=:avail_balance,leave_type =:leave_type,leave_status =:leave_status,leave_description =:leave_description,leave_id =:leave_id where"+"emp_id =:emp_id")
    void updateLeave(@Bind("leave_from_date") String leave_from_date,@Bind("leave_to_date") String leave_to_date,@Bind("avail_balance") int bal,@Bind("leave_type") String leave_type,@Bind("leave_status") String leaveStatus,
    @Bind("leave_description")	String leave_description,@Bind("leave_id") int leave_id);
 
   @SqlQuery("SELECT e1.emp_id from Employee e1"
        + " JOIN Employee e2 ON e1.emp_id=e2.Manager_id where e2.emp_id =(SELECT emp_id from leavess "
        + "   where leave_id=:leave_id)")
   int showManager(@Bind("leave_id") int leave_id);

   @SqlUpdate("insert into leavess (leave_id,emp_id, leave_from_date,leave_to_date, "
   +" Leave_description, ls, lt, applied_leave)values"
   +"(:leave_id, :emp_id, :leave_from_date, :leave_to_date, :leave_description, "
   +":leave_status, :leave_type, :leave_applied_on)")
void apply_leave(
   @Bind("leave_id")int leave_id,
   @Bind("emp_id") int emp_id,
   @Bind("leave_from_date") java.util.Date leave_from_date,
   @Bind("leave_to_date") java.util.Date leave_to_date,
   @Bind("leave_description") String leave_description,
   @Bind("leave_status") EnumLeaveStatus leave_status,
   @Bind("leave_type") EnumLeaveType leave_type,
   @Bind("leave_applied_on") java.util.Date applied_date);


   
   
   @SqlQuery("SELECT count(*) from leavess "
   +    " where emp_id = :emp_id AND (leave_from_date between :leave_from_date AND :leave_to_date "
   +    " OR leave_to_date between :leave_from_date AND :leave_to_date)"
   )
int count(@Bind("emp_id") int emp_id, @Bind("leave_from_date") java.util.Date leave_from_date,
   @Bind("leave_to_date") java.util.Date leave_to_date);

   


@SqlQuery("select * from leavess order by leave_id desc limit 1")
@Mapper(LeaveMapper.class)
Leave getlastrow();


}
