package com.hexaware.MLP205.persistence;

import java.util.List;

import com.hexaware.MLP205.model.Leave;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;


public interface LeaveDao{   
    
  @SqlQuery("select * from leave where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  List<Leave> list();
    
  @SqlQuery("select * from leave where leave_id = :leave_id")
  @Mapper(LeaveMapper.class)
  Leave find(@Bind("leave_id") int leave_id);
  

  @SqlQuery("select designation,leave_id from employee,leave where emp_id=:emp_id")
  @Mapper(LeaveMapper.class)
  Leave show(@Bind("leave_id") int leave_id,@Bind("designation")String designation);

 @SqlQuery("select * from leave where emp_id =:emp_id")
 @Mapper(LeaveMapper.class)
 List<Leave>leaveHistory(@Bind("emp_id")int emp_id);
 
 @SqlUpdate("insert into Leave "+"emp_id"+"leave_to_date"+
  "leave_from_date"+"no_leave"+"leave_type"+"leave_status"+
  "leave_description"+"available_leave"+
  "value("+":emp_Id"+"leave_to_date"+":leave_from_date"+":no_leave"+":leave_type"+":leave_status"+
  ":leave_description"+"available_leave")
   void apply(@Bind("emp_id") int emp_id,
   @Bind("leave_from_date") String lfddate,
   @Bind("leave_to_date") String ltddate,
   @Bind("no_leave") int no_leave,
   @Bind("leave_type") String leave_type,
   @Bind("leave_status") String leave_status,
   @Bind("leave_description") String leave_description,
   @Bind("applied_on_date") String applied_on_date,
   @Bind("available_leave")int available_leave);

   @SqlUpdate("UPDATE LEAVE SET COMMENTS = :leavemanagercomm, LEAVE_STATUS = :leavestatus WHERE "
      + "leave_id = :leaveid")
  void comment(@Bind("leavemanagercomm") String leavemanagercomm, @Bind("leave_status") String leavestatus,
      @Bind("leave_id") int leave_id);
@SqlUpdate("Update leave set leave_from_date =:leave_manager,leave_to_date =:leave_to_date,avail_balance=:avail_balance,leave_type =:leave_type,leave_status =:leave_status,leave_description =:leave_description,leave_id =:leave_id where"+"emp_id =:emp_id")
void updateLeave(@Bind("leave_from_date") String leave_from_date,@Bind("leave_to_date") String leave_to_date,@Bind("avail_balance") int bal,@Bind("leave_type") String leave_type,@Bind("leave_status") String leaveStatus,
  @Bind("leave_description")	String leave_description,@Bind("leave_id") int leave_id);
  @SqlQuery("SELECT e1.emp_id from Employee e1"
        + " JOIN Employee e2 ON e1.emp_id=e2.Manager_id where e2.emp_id =(SELECT emp_id from leave "
        + "   where leave_id=:leave_id)")
    List<Leave> showManager(@Bind("leave_id") int leave_id);





}
